package com.example.ExchangeRates.gui;

import com.example.ExchangeRates.DTO.Currency;
import com.example.ExchangeRates.DTO.RateDTO;
import com.example.ExchangeRates.DTO.RateRecord;
import com.example.ExchangeRates.data.ExchangeRatesDataService;
import com.example.ExchangeRates.web.ExchangeService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static javafx.scene.chart.XYChart.*;

@Component
@FxmlView("main.fxml")
public class FxController implements Initializable  {

    @FXML
    private TableView<RateDTO> currencies;
    @FXML
    private TableColumn<RateDTO, String> currency;
    @FXML
    private TableColumn<RateDTO, String> cc;
    @FXML
    private TableColumn<RateDTO, BigDecimal> rate;
    @FXML
    private TextField filter;
    @FXML
    private ComboBox<Currency> currentCurrency;
    @FXML
    private DatePicker dateFrom;
    @FXML
    private DatePicker dateTo;
    @FXML
    private LineChart diagram;
    @FXML
    private NumberAxis yAxis;

    @Autowired
    private ExchangeRatesDataService exchangeRatesDataService;
    @Autowired
    private ExchangeService exchangeService;

    private FilteredList<RateDTO> filteredList;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        currency.setCellValueFactory(new PropertyValueFactory<>("txt"));
        cc.setCellValueFactory(new PropertyValueFactory<>("cc"));

        rate.setCellValueFactory(new PropertyValueFactory<>("rate"));
        rate.setCellFactory(rateDTOBigDecimalTableColumn -> new TableCell<>() {
            @Override
            protected void updateItem(BigDecimal item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("");
                } else {
                    setText(item.toString());
                }
            }
        });

        loadValues();

        filter.setPromptText("<input your filter>");
        filter.textProperty().addListener(
                (observableValue, s, t1) ->
                        filteredList.setPredicate(
                            rateDTO ->
                                    rateDTO.getCc().contains(filter.getText())
                                    || rateDTO.getTxt().contains(filter.getText())));

        dateFrom.setValue(LocalDate.now().withDayOfMonth(1));
        dateTo.setValue(LocalDate.now());

        drawDiagram();

    }

    private void loadValues() {
        List<RateDTO> lastRates = exchangeRatesDataService.getLastRates();
        filteredList = new FilteredList<>(
                FXCollections.observableArrayList(lastRates),
                rateDTO ->
                        rateDTO.getCc().contains(filter.getText())
                                || rateDTO.getTxt().contains(filter.getText()));
        SortedList<RateDTO> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(currencies.comparatorProperty());
        currencies.setItems(sortedList);

        currentCurrency.getItems().addAll(exchangeRatesDataService.getCurrencies());

    }

    public void loadNewData(ActionEvent actionEvent) {
        exchangeRatesDataService.save(exchangeService.getRates());
        loadValues();
    }

    private void drawDiagram() {

        yAxis.setAutoRanging(true);

        Series dataSeries = new Series();
        dataSeries.setName("Nothing");

        diagram.getData().clear();
        diagram.getData().add(dataSeries);
        diagram.setAnimated(false);

        if (currentCurrency.getValue()!=null) {
            BigDecimal minRate=null, maxRate=null;
            dataSeries.setName(currentCurrency.getValue().getDescription());
            exchangeRatesDataService.save(exchangeService.getRatesCurrencyInRange(
                    currentCurrency.getValue(),
                    dateFrom.getValue(),
                    dateTo.getValue()
            ));
            List<RateRecord> records = exchangeRatesDataService.getRatesInRange(
                    currentCurrency.getValue(),
                    Date.valueOf(dateFrom.getValue()),
                    Date.valueOf(dateTo.getValue()));
            for (RateRecord record: records) {
                Data item = new Data(record.getDate().toString(), record.getRate());
                item.nodeProperty().addListener(new ChangeListener<Node>() {
                    @Override
                    public void changed(ObservableValue<? extends Node> observableValue, Node node, Node newValue) {
                        if (newValue != null) {
                            Tooltip.install(newValue, new Tooltip(
                                    item.getYValue().toString()+'\n'+item.getXValue()));
                            item.nodeProperty().removeListener(this);
                        }
                    }
                });
                dataSeries.getData().add(item);

                if (minRate == null) {
                    minRate = record.getRate();
                    maxRate = record.getRate();
                } else {
                    minRate = minRate.min(record.getRate());
                    maxRate = maxRate.max(record.getRate());
                }
            }
            if (!Objects.equals(minRate, maxRate)) {
                BigDecimal delta = maxRate.subtract(minRate);
                minRate = minRate.subtract(delta.divide(BigDecimal.valueOf(2)));
                maxRate = maxRate.add(delta.divide(BigDecimal.valueOf(2)));
                yAxis.setAutoRanging(false);
                yAxis.setLowerBound(minRate.doubleValue());
                yAxis.setUpperBound(maxRate.doubleValue());
                yAxis.setTickUnit((maxRate.doubleValue()-minRate.doubleValue())/10);
            }
        }
    }

    public void drawNewDiagram(ActionEvent actionEvent) {
        drawDiagram();
    }

}
