/**********************************************************
 *                                                        *
 *  CSCI 470/502        Assignment 6         Summer 2021  *
 *                                                        *
 *  Class Name:  MileRedeemerGuiAppController             *
 *                                                        *
 *  Developer(s):  Leonart Jaos                           *
 *                                                        *
 *  Purpose: The controller that populates the GUI text fi*
 *  elds. Includes a file chooser, which allows the User  *
 *  to select the text file for data. The data is parsed  *
 *  and the city names that a user can travel to are displ*
 *  ayed. The user selects the city name and an event list*
 *  ener changes the displayed data before the list of cit*
 *  y names. Then the user inputs their current miles and *
 *  selects the month of travel in the spinner and the bes*
 *  t trip selection is made and displayed in a ListView  *
 *  when the user presses "Redeem". The remaining miles ar*
 *  e displayed on the bottom-most text field.            *
 *                                                        *
 **********************************************************/



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class MileRedeemerGuiAppController {
    @FXML
    private ListView<String> DestListView; // List view for city names

    @FXML
    private TextField NmilesText; // Textfield for normal travel miles requirement

    @FXML
    private TextField UcostText; // Textfield to display upgrade cost from economy to first for location

    @FXML
    private TextField SmilesText; // Textfield to display offseason miles cost

    @FXML
    private TextField SdatesText; // Text field to display offseason start and end months

    @FXML
    private Spinner<String> Mselect; // spinner that allows user to select start month

    @FXML
    private TextField EmilesText; // Text field for user to enter their current miles

    @FXML
    private Button Rdmiles; // button for redeem miles

    @FXML
    private ListView<String> AlgListView; // List view to show best possible flight selection

    @FXML
    private TextField RmilesText; // Textfield for remaining miles

    private String filename; // string var for filename used in class


    // stores the list of Destination Objects
    private final ObservableList<String> Garray =
            FXCollections.observableArrayList();

    // unused setter function
    public void setFilename(String filename)
    {
        this.filename = filename;
    }

    // Populates GUI
    public void initialize() throws IOException {
        // Create frame object for file selector
        final JFrame frame = new JFrame("File Selection");
        JFileChooser f = new JFileChooser();
        // set the title
        f.setDialogTitle("Opening the location..");
        // open the dialog box
        f.showOpenDialog(frame);
        HBox r = new HBox();
        r.setSpacing(20);
        // create scene
        Scene sc = new Scene(r,350,100);
        // set filename var to selected user file
        filename = f.getSelectedFile().toString();
        // Parse input file
        Scanner destinations = new Scanner(new File(filename));
        MileRedeemer MR = new MileRedeemer();
        MR.readDestinations(destinations);
        // add city names to observable ListView from file
        for (String ind : MR.getCityNames()) {
            Garray.add(ind);
        }
        // bind listview to observable list view
        DestListView.setItems(Garray);

        // action listener that assigns values to textfields upon city selection
        DestListView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            try {
                changeVals(DestListView.getSelectionModel().getSelectedItem());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
        // observable list to populate spinner
        ObservableList<String> months = FXCollections.observableArrayList(//
                "Jan", "Feb", "Mar", "Apr", //
                "May", "Jun", "Jul", "Aug", //
                "Sep", "Oct", "Nov", "Dec");
        // Value factory.
        SpinnerValueFactory<String> valueFactory = //
                new SpinnerValueFactory.ListSpinnerValueFactory<String>(months);
        // default value
        valueFactory.setValue("Jan");
        // assign values to spinner
        Mselect.setValueFactory(valueFactory);
        // on buttonpress, redeem miles with user input miles and spinner selection
        Rdmiles.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)
            {
                // observable array for second list view that displays redeem miles results
                ObservableList<String> Garray2 =
                        FXCollections.observableArrayList();
                int a;
                String [] clientRes = new String[MR.redeemMiles(Integer.parseInt(EmilesText.getText()),montoNum(Mselect.getValue())).length];
                clientRes = MR.redeemMiles(Integer.parseInt(EmilesText.getText()),montoNum(Mselect.getValue()));
                // if returned result from mile redemption is empty, insufficient miles
                if(clientRes.length == 0)
                {
                    Garray2.add("*** Your client has not accumulated enough Frequent Flyer Miles ***");
                }
                else
                {
                    Garray2.add("Your client's Frequent Flyer Miles can be used to redeem the following tickets:");
                    a = 0;
                    for(int i = 0; i < (clientRes.length)/2; i++)
                    {
                        if(!clientRes[a + 1].equals("Not eligible"))
                        {
                            Garray2.add("* A trip to " + clientRes[a] + " in " + clientRes[a+1]);
                        }
                        a = a + 2;
                    }
                }
                // bind observable list to ListView
                AlgListView.setItems(Garray2);
                // set remaining miles text field
                RmilesText.setText(Integer.toString(MR.getRmls()));
            }
        });

    }
    // function that sets values of Normal miles, supersaver miles, upgrade cost, and
    // offseason months. Called when city name is selected
    public void changeVals(String str) throws IOException
    {
        // parse file again for values
        String dates = "";
        Scanner destinations = new Scanner(new File(filename));
        MileRedeemer MR = new MileRedeemer();
        MR.readDestinations(destinations);
        Destination check = new Destination();
        for (Destination ind : MR.getArrList()) {
            if (ind.getCty().equals(str)) {
                check = ind;
            }
        }
        // set textfields to values from file based on city selection
        NmilesText.setText(Integer.toString(check.getMls()));
        UcostText.setText(Integer.toString(check.getAdMls()));
        SmilesText.setText(Integer.toString(check.getOsMls()));
        dates = numToMon(check.getStrMth()) + " - " + numToMon(check.getEndMth());
        SdatesText.setText(dates);

    }
    // converts an integer to a month abbreviation
    public String numToMon(int num)
    {
        switch (num)
        {
            case 1:
                return "Jan";
            case 2:
                return "Feb";
            case 3:
                return "Mar";
            case 4:
                return "Apr";
            case 5:
                return "May";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Aug";
            case 9:
                return "Sep";
            case 10:
                return "Oct";
            case 11:
                return "Nov";
            case 12:
                return "Dec";
            default:
                return "Inv";
        }
    }
    // converts a string month abbreviation to an integer
    public int montoNum(String mon)
    {
        switch (mon)
        {
            case "Jan":
                return 1;
            case "Feb":
                return 2;
            case "Mar":
                return 3;
            case "Apr":
                return 4;
            case "May":
                return 5;
            case "Jun":
                return 6;
            case "Jul":
                return 7;
            case "Aug":
                return 8;
            case "Sep":
                return 9;
            case "Oct":
                return 10;
            case "Nov":
                return 11;
            case "Dec":
                return 12;
            default:
                return -1;
        }
    }

}