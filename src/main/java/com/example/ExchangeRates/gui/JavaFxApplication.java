package com.example.ExchangeRates.gui;

import com.example.ExchangeRates.ExchangeRatesApplication;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class JavaFxApplication extends Application {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);

        this.applicationContext = new SpringApplicationBuilder()
                .sources(ExchangeRatesApplication.class)
                .run(args);
    }

    @Override
    public void start(Stage stage) {

        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        Parent root = fxWeaver.loadView(FxController.class);
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.setTitle("Exchange rates from bank.gov.ua");

        Image imageCurrency = new Image(getClass().getResourceAsStream("/static/currency.png"));
        stage.getIcons().add(imageCurrency);

        stage.show();
    }

    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
    }

}