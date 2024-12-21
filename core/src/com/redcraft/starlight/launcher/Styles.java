package com.redcraft.starlight.launcher;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Disposable;
import com.redcraft.starlight.util.Files;

public class Styles implements Disposable {
    BitmapFont font;
    Skin skin;
    TextField.TextFieldStyle textFieldStyle;
    TextButton.TextButtonStyle textButtonStyle;
    List.ListStyle listStyle;
    Label.LabelStyle labelStyle;
    Window.WindowStyle windowStyle;
    int fontSize;

    public Styles(String fontName, int fontSize, String skinName) {
        this.fontSize = fontSize;
        font = Files.font(fontName,fontSize);
        skin = new Skin(Gdx.files.internal("skins/"+skinName+"/uiskin.json"));
        textFieldStyle = skin.get(TextField.TextFieldStyle.class);
        textFieldStyle.font = font;
        textButtonStyle = skin.get(TextButton.TextButtonStyle.class);
        textButtonStyle.font = font;
        listStyle = skin.get(List.ListStyle.class);
        listStyle.font = font;
        labelStyle = skin.get(Label.LabelStyle.class);
        labelStyle.font = font;
        windowStyle = skin.get(Window.WindowStyle.class);
    }

    public TextField.TextFieldStyle getTextFieldStyle() {
        return textFieldStyle;
    }
    public TextButton.TextButtonStyle getTextButtonStyle() {
        return textButtonStyle;
    }
    public List.ListStyle getListStyle() {
        return listStyle;
    }
    public Label.LabelStyle getLabelStyle() {
        return labelStyle;
    }
    public Window.WindowStyle getWindowStyle() {
        return windowStyle;
    }
    public Skin getSkin() {
        return skin;
    }
    public int getFontSize() {
        return fontSize;
    }
    @Override
    public void dispose() {
        font.dispose();
        skin.dispose();
    }
}
