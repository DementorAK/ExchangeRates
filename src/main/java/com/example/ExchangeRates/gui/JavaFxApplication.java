package com.example.ExchangeRates.gui;

import com.example.ExchangeRates.ApplicationContextHolder;
import com.example.ExchangeRates.ExchangeRatesApplication;
import com.example.ExchangeRates.lang.I18n;
import com.example.ExchangeRates.lang.Languages;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;

public class JavaFxApplication extends Application {

    private ConfigurableApplicationContext applicationContext;

    private static Languages currentLanguage = Languages.EN;
    private static Stage stage;

    public static void setCurrentLanguage(Languages language) {
        currentLanguage = language;
        LocaleContextHolder.setLocale(language.locale);
        LocaleContextHolder.setDefaultLocale(language.locale);
    }

    public static Languages getCurrentLanguage() {
        return currentLanguage;
    }

    @Override
    public void init() {
        setCurrentLanguage(Languages.EN);
        String[] args = getParameters().getRaw().toArray(new String[0]);
        this.applicationContext = new SpringApplicationBuilder()
                .sources(ExchangeRatesApplication.class)
                .run(args);
    }

    @Override
    public void start(Stage stage) {

        JavaFxApplication.stage = stage;

        Image imageCurrency = new Image(getClass().getResourceAsStream("/static/currency.png"));
        stage.getIcons().add(imageCurrency);

        loadScene();
        stage.show();
    }

    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
    }

    public static void loadScene(){

        I18n i18n = ApplicationContextHolder.getApplicationContext().getBean(I18n.class);
        stage.setTitle(i18n.getMessage("title"));

        FxWeaver fxWeaver = ApplicationContextHolder.getApplicationContext().getBean(FxWeaver.class);
        Parent root = fxWeaver.loadView(FxController.class, i18n.getBundle());
        Scene scene = new Scene(root);
        stage.setScene(scene);

    }

}
