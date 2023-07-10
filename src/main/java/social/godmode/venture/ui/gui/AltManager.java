package social.godmode.venture.ui.gui;

import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import fr.litarvan.openauth.microsoft.model.response.MinecraftProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.*;
import net.minecraft.util.Session;
import org.json.JSONObject;
import org.lwjgl.input.Keyboard;
import social.godmode.venture.Venture;
import social.godmode.venture.ui.field.GuiPasswordField;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AltManager extends GuiScreen {

    @Getter
    private final File altFile = new File(Venture.NAME + "\\AltManager", "alts.json");

    @Getter
    private JSONObject altFileJson;

    @Getter
    private GuiTextField emailField;

    @Getter
    private GuiPasswordField passwordField;

    @Getter
    private String text = "";

    @Getter
    private final GuiScreen parent;

    public AltManager(GuiScreen parent) {
        this.checkFile();
        this.parent = parent;
    }

    @Override
    public void initGui() {
        ScaledResolution sr = new ScaledResolution(mc);
        this.altFileJson = new JSONObject(altFile);

        this.emailField = new GuiTextField(0, mc.fontRendererObj, sr.getScaledWidth() / 2 - 100, sr.getScaledHeight() / 2 - 50, 200, 20);
        this.passwordField = new GuiPasswordField(1, mc.fontRendererObj, sr.getScaledWidth() / 2 - 100, sr.getScaledHeight() / 2 - 25, 200, 20);
        this.buttonList.add(new GuiButton(0, sr.getScaledWidth() / 2 - 100, sr.getScaledHeight() / 2 + 25, "Login"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        passwordField.drawTextBox();
        emailField.drawTextBox();

        ScaledResolution sr = new ScaledResolution(mc);
        this.drawCenteredString(mc.fontRendererObj, "Alt Manager", sr.getScaledWidth() / 2, sr.getScaledHeight() / 2 - 75, 0xFFFFFF);

        if(!text.isEmpty()) this.drawCenteredString(mc.fontRendererObj, text, sr.getScaledWidth() / 2, sr.getScaledHeight() / 2 + 50, 0xFFFFFF);

        this.drawString(mc.fontRendererObj, "Email:", (sr.getScaledWidth() / 2 - 100) - (mc.fontRendererObj.getStringWidth("Email:")+5), (sr.getScaledHeight() / 2 - 50)+(FontRenderer.FONT_HEIGHT -4), 0xFFFFFF);
        this.drawString(mc.fontRendererObj, "Password:", (sr.getScaledWidth() / 2 - 100) - (mc.fontRendererObj.getStringWidth("Password:")+5), (sr.getScaledHeight() / 2 - 25)+(FontRenderer.FONT_HEIGHT -4), 0xFFFFFF);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if(emailField.isFocused()) emailField.textboxKeyTyped(typedChar, keyCode);
        if(passwordField.isFocused()) passwordField.textboxKeyTyped(typedChar, keyCode);

        if(keyCode == Keyboard.KEY_ESCAPE) {
            this.mc.displayGuiScreen(parent);
            return;
        }

        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        emailField.mouseClicked(mouseX, mouseY, mouseButton);
        passwordField.mouseClicked(mouseX, mouseY, mouseButton);

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            if (emailField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                text = "Please fill in all fields!";
                return;
            }

            text = "Logging in...";
            new Thread(() -> {
                MicrosoftAccount account = loginMicrosoft(emailField.getText(), passwordField.getText());

                if(account == null) {
                    text = "Invalid credentials!";
                    return;
                }

                if(!altFileJson.has("alts") || (!(altFileJson.get("alts") instanceof JSONObject))) altFileJson.put("alts", new JSONObject());

                if(altFileJson.getJSONObject("alts").has(account.getProfile().getName())) {
                    JSONObject alts = altFileJson.getJSONObject("alts");
                    alts.remove(account.getProfile().getName());
                    altFileJson.put("alts", alts);
                }

                JSONObject alts = altFileJson.getJSONObject("alts");
                alts.put(account.getProfile().getName(), account.getRefreshToken());
                altFileJson.put("alts", alts);

                try(FileWriter writer = new FileWriter(altFile)) {
                    writer.write(altFileJson.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                text = "Logged in as " + account.getProfile().getName();
            }).start();
        }

        super.actionPerformed(button);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void checkFile() {
        File parent = altFile.getParentFile();
        if(!parent.exists()) parent.mkdirs();

        if(!altFile.exists()) {
            try(FileWriter writer = new FileWriter(altFile)) {
                writer.write("{\"alts\": []}");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        JSONObject jsonObject = new JSONObject(altFile);

        if(!jsonObject.has("alts")) jsonObject.put("alts", new JSONObject());

        try(FileWriter writer = new FileWriter(altFile)) {
            writer.write(jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MicrosoftAccount loginMicrosoft(String email, String password) {
        try {
            MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
            MicrosoftAuthResult result = authenticator.loginWithCredentials(email, password);
            MinecraftProfile profile = result.getProfile();

            Session session = new Session(profile.getName(), profile.getId(), result.getAccessToken(), "mojang");
            mc.session = session;
            return new MicrosoftAccount(profile, session, result.getRefreshToken());
        } catch (Exception ignored) {}
        return null;
    }

    @Getter @Setter
    @AllArgsConstructor
    public static class MicrosoftAccount {
        private MinecraftProfile profile;
        private Session session;
        private String refreshToken;
    }

}