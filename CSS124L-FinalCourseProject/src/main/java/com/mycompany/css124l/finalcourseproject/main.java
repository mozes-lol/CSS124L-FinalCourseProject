/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.css124l.finalcourseproject;

import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author John Mark Garcia
 */

public class main extends javax.swing.JFrame {
    
    private JButton currentlySelectedSeat = null; // Store the selected seat
    private int[] currentlySelectedSeatIndex = {0, 0}; // will involve two numbers (row and column) that will go in tandem with the variable above
    static String[][] F1SeatList = {
        {"vacant", "vacant"},
        {"vacant", "vacant"},
        {"vacant", "vacant"},
        {"vacant", "vacant"}
    };
    static String[][] F2SeatList = {
        {"vacant", "vacant"},
        {"vacant", "vacant"},
        {"vacant", "vacant"},
        {"vacant", "vacant"}
    };
    // I really needed this to visualize what the hell is going on in the seat list
    public void ConsoleCheckSeatList(String[][] SeatList) {
        for (int row = 0; row < 4; row++) {
            System.out.println("\n");
            for (int col = 0; col < 2; col++) {
                System.out.print(SeatList[row][col] + " ");
            }
        }
    }
    /*
    The problem I noticed is the seat in the seat list remained "selected"
    despite already selecting a different button. To fix this, we'll remove all
    seats in the seatList array that has the value "selected".
    */
    public void ClearSelectedSeats(String[][] SeatList) {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 2; col++) {
                if (SeatList[row][col] == "selected") // this assumems that all seats that could be incorrectly clicked are "vacant"
                {
                    SeatList[row][col] = "vacant";
                }
            }
        }
    }

    public main() {
        initComponents();
        updateFlightCostSummary();
        UpdateSeatList(F1SeatList); // Check seat availability on startup
        b_seat_1a.setToolTipText("Click to select seat 1A");
        b_confirm.setEnabled(false);
    }
    
    public void UpdateSeatList(String[][] SeatList) {
        JButton[][] buttons = {
            {b_seat_1a, b_seat_1b},
            {b_seat_2a, b_seat_2b},
            {b_seat_3a, b_seat_3b},
            {b_seat_4a, b_seat_4b}
        };

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 2; col++) {
                if (SeatList[row][col].equals("vacant")) {
                    buttons[row][col].setBackground(Color.GREEN);
                } else {
                    buttons[row][col].setBackground(Color.RED);
                }
            }
        }
    }
    private void checkFormCompletion() {
    
    boolean isComplete = !tf_name.getText().trim().isEmpty() &&
                         !tf_age.getText().trim().isEmpty() &&
                         !tf_address.getText().trim().isEmpty() &&
                         cb_nationality.getSelectedIndex() != 0 &&
                         (rb_male.isSelected() || rb_female.isSelected() || rb_others.isSelected()) &&
                         !tf_passportNumber.getText().trim().isEmpty() &&
                         !tf_contactNumber.getText().trim().isEmpty() &&
                         !tf_emailAddress.getText().trim().isEmpty() &&
                         !tf_dateofflight.getText().trim().isEmpty() &&
                         cb_mealPreference.getSelectedIndex() != 0 &&
                         cb_departureAndDestination.getSelectedIndex() != 0 &&
                         currentlySelectedSeat != null; 

    
    b_confirm.setEnabled(isComplete);
}
    private void updateFlightCostSummary() {
    String selectedDestination = cb_departureAndDestination.getSelectedItem().toString();
    
    // Ensure the default destination is selected if not manually chosen
    if (selectedDestination.equals("Select Destination")) {
        selectedDestination = "Bicol International Airport"; // Set a default
        cb_departureAndDestination.setSelectedItem(selectedDestination);
    }
    
    int baseFare = getBaseFare(selectedDestination);
    int additionalCost = 0;

    if (chb_extraBaggage.isSelected()) additionalCost += 500;
    if (chb_extraSnacksAndDrinks.isSelected()) additionalCost += 200;
    if (chb_inflightWifiAccess.isSelected()) additionalCost += 300;
    if (chb_priorityCheckinAndBoarding.isSelected()) additionalCost += 400;

    int totalCost = baseFare + additionalCost;

    // Update the text area with the cost breakdown
    ta_flightCostSummary.setText("Destination: " + selectedDestination + "\n"
                               + "Base Fare: ₱" + baseFare + "\n"
                               + "Additional Charges: ₱" + additionalCost + "\n"
                               + "Total Cost: ₱" + totalCost);
}
private int getBaseFare(String destination) {
    switch (destination) {
        case "Bicol International Airport": return 2000;
        case "Clark International Airport": return 2500;
        case "Mactan-Cebu International Airport": return 3000;
        case "NAIA": return 3500;
        default: return 0; // If no valid destination is selected
    }
}


private void seatMouseEntered(JButton seat) {
    if (seat.getBackground() == Color.GREEN) {
        seat.setBackground(new Color(144, 238, 144)); // Lighter green when hovered
    } 
    seat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR)); // Change cursor
}

private void seatMouseExited(JButton seat) {
    if (seat.getBackground().equals(new Color(144, 238, 144))) {
        seat.setBackground(Color.GREEN); // Reset to original color
    }
}

    private void seatButtonClicked(JButton seat, int row, int col) {
        // Prevent the user from selecting occupied seats
        if (seat.getBackground() == Color.RED) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        /*
        No need to check which flight number is currently selected. We'll just
        clear both.
        */
        ClearSelectedSeats(F1SeatList);
        ClearSelectedSeats(F2SeatList);
        if (currentlySelectedSeat != null && currentlySelectedSeat.getBackground() != Color.RED) {
            currentlySelectedSeat.setBackground(Color.GREEN); // Reset previous seat color
        }
        currentlySelectedSeat = seat;
        currentlySelectedSeat.setBackground(Color.YELLOW); // Highlight selected seat
        currentlySelectedSeatIndex[0] = row;
        currentlySelectedSeatIndex[1] = col;
        // Mark the seat as "selected" internally
        F1SeatList[row][col] = "selected"; 
        System.out.println(F1SeatList);
        System.out.println("Selected Seat: " + seat.getText());
        checkFormCompletion();
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gender = new javax.swing.ButtonGroup();
        p_main = new javax.swing.JPanel();
        p_header = new javax.swing.JPanel();
        l_flightNumber = new javax.swing.JLabel();
        comB_flightNumber = new javax.swing.JComboBox<>();
        p_body = new javax.swing.JPanel();
        l_name = new java.awt.Label();
        l_address = new java.awt.Label();
        tf_name = new java.awt.TextField();
        tf_address = new java.awt.TextField();
        l_age = new java.awt.Label();
        tf_age = new java.awt.TextField();
        l_nationality = new java.awt.Label();
        l_gender = new java.awt.Label();
        rb_male = new javax.swing.JRadioButton();
        cb_nationality = new javax.swing.JComboBox<>();
        rb_female = new javax.swing.JRadioButton();
        rb_others = new javax.swing.JRadioButton();
        l_passportNumber = new java.awt.Label();
        l_contactNumber = new java.awt.Label();
        tf_passportNumber = new java.awt.TextField();
        tf_contactNumber = new java.awt.TextField();
        l_emailAddress = new java.awt.Label();
        tf_emailAddress = new java.awt.TextField();
        l_dateOfFlight = new java.awt.Label();
        tf_dateofflight = new java.awt.TextField();
        l_mealPreference = new java.awt.Label();
        cb_mealPreference = new javax.swing.JComboBox<>();
        l_additionalServices = new java.awt.Label();
        chb_pwdAssistance = new javax.swing.JCheckBox();
        chb_extraBaggage = new javax.swing.JCheckBox();
        chb_extraSnacksAndDrinks = new javax.swing.JCheckBox();
        chb_inflightWifiAccess = new javax.swing.JCheckBox();
        chb_priorityCheckinAndBoarding = new javax.swing.JCheckBox();
        l_flightCostSummary = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ta_flightCostSummary = new javax.swing.JTextArea();
        l_departureAndDestination = new javax.swing.JLabel();
        cb_departureAndDestination = new javax.swing.JComboBox<>();
        l_addtionalNotes = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        ta_additionalNotes = new javax.swing.JTextArea();
        p_footer = new javax.swing.JPanel();
        b_clear = new javax.swing.JButton();
        b_confirm = new javax.swing.JButton();
        p_seats = new javax.swing.JPanel();
        p_seat_1a = new javax.swing.JPanel();
        b_seat_1a = new javax.swing.JButton();
        p_seat_1b = new javax.swing.JPanel();
        b_seat_1b = new javax.swing.JButton();
        p_seat_2a = new javax.swing.JPanel();
        b_seat_2a = new javax.swing.JButton();
        p_seat_2b = new javax.swing.JPanel();
        b_seat_2b = new javax.swing.JButton();
        p_seat_3a = new javax.swing.JPanel();
        b_seat_3a = new javax.swing.JButton();
        p_seat_3b = new javax.swing.JPanel();
        b_seat_3b = new javax.swing.JButton();
        p_seat_4a = new javax.swing.JPanel();
        b_seat_4a = new javax.swing.JButton();
        p_seat_4b = new javax.swing.JPanel();
        b_seat_4b = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(800, 600));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        p_main.setBorder(javax.swing.BorderFactory.createTitledBorder("Passenger Details"));
        p_main.setMinimumSize(new java.awt.Dimension(600, 600));
        p_main.setPreferredSize(new java.awt.Dimension(600, 600));
        p_main.setLayout(new java.awt.BorderLayout());

        l_flightNumber.setText("Flight Number");
        p_header.add(l_flightNumber);

        comB_flightNumber.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "F1", "F2" }));
        comB_flightNumber.setMinimumSize(new java.awt.Dimension(100, 22));
        comB_flightNumber.setPreferredSize(new java.awt.Dimension(100, 22));
        comB_flightNumber.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comB_flightNumberItemStateChanged(evt);
            }
        });
        p_header.add(comB_flightNumber);

        p_main.add(p_header, java.awt.BorderLayout.PAGE_START);

        l_name.setText("Name:");

        l_address.setText("Address:");

        tf_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_nameActionPerformed(evt);
            }
        });

        tf_address.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_addressActionPerformed(evt);
            }
        });

        l_age.setText("Age:");

        tf_age.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_ageActionPerformed(evt);
            }
        });
        tf_age.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_ageKeyTyped(evt);
            }
        });

        l_nationality.setText("Nationality:");

        l_gender.setText("Gender:");

        gender.add(rb_male);
        rb_male.setText("Male");
        rb_male.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rb_maleItemStateChanged(evt);
            }
        });
        rb_male.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_maleActionPerformed(evt);
            }
        });

        cb_nationality.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select a Nationality", "Filipino", "American", "American", "Chinese", "Filipino", "Japanese", "Korean", "Singaporean", "Spanish" }));
        cb_nationality.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_nationalityActionPerformed(evt);
            }
        });

        gender.add(rb_female);
        rb_female.setText("Female");
        rb_female.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rb_femaleItemStateChanged(evt);
            }
        });
        rb_female.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_femaleActionPerformed(evt);
            }
        });

        gender.add(rb_others);
        rb_others.setText("Others");
        rb_others.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rb_othersItemStateChanged(evt);
            }
        });
        rb_others.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_othersActionPerformed(evt);
            }
        });

        l_passportNumber.setText("Passport Number:");

        l_contactNumber.setText("Contact Number:");

        tf_passportNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_passportNumberActionPerformed(evt);
            }
        });
        tf_passportNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_passportNumberKeyTyped(evt);
            }
        });

        tf_contactNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_contactNumberActionPerformed(evt);
            }
        });
        tf_contactNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_contactNumberKeyTyped(evt);
            }
        });

        l_emailAddress.setText("Email Address:");

        tf_emailAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_emailAddressActionPerformed(evt);
            }
        });

        l_dateOfFlight.setText("Date of Flight:");

        tf_dateofflight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_dateofflightActionPerformed(evt);
            }
        });

        l_mealPreference.setText("Meal Preference:");

        cb_mealPreference.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select a Meal", "Regular Meal", "Vegetarian Meal", "Kiddie Meal", "Salad Meal" }));
        cb_mealPreference.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_mealPreferenceActionPerformed(evt);
            }
        });

        l_additionalServices.setText("Additional Services:");

        chb_pwdAssistance.setText("PWD assistance");
        chb_pwdAssistance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chb_pwdAssistanceActionPerformed(evt);
            }
        });

        chb_extraBaggage.setText("Extra Baggage");
        chb_extraBaggage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chb_extraBaggageActionPerformed(evt);
            }
        });

        chb_extraSnacksAndDrinks.setText("Extra Snacks & Drinks");
        chb_extraSnacksAndDrinks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chb_extraSnacksAndDrinksActionPerformed(evt);
            }
        });

        chb_inflightWifiAccess.setText("In-Flight Wi-Fi Access");
        chb_inflightWifiAccess.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chb_inflightWifiAccessActionPerformed(evt);
            }
        });

        chb_priorityCheckinAndBoarding.setText("Priority Check in & Boarding");
        chb_priorityCheckinAndBoarding.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chb_priorityCheckinAndBoardingActionPerformed(evt);
            }
        });

        l_flightCostSummary.setText("Flight Cost Summary:");

        ta_flightCostSummary.setColumns(20);
        ta_flightCostSummary.setRows(5);
        jScrollPane1.setViewportView(ta_flightCostSummary);

        l_departureAndDestination.setText("Departure & Destination:");

        cb_departureAndDestination.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Destination", "Bicol International Airport", "Clark International Airport", "Mactan-Cebu International Airport", "NAIA" }));
        cb_departureAndDestination.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_departureAndDestinationActionPerformed(evt);
            }
        });

        l_addtionalNotes.setText("Additional Notes:");

        ta_additionalNotes.setColumns(20);
        ta_additionalNotes.setRows(5);
        jScrollPane2.setViewportView(ta_additionalNotes);

        javax.swing.GroupLayout p_bodyLayout = new javax.swing.GroupLayout(p_body);
        p_body.setLayout(p_bodyLayout);
        p_bodyLayout.setHorizontalGroup(
            p_bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_bodyLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(p_bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(p_bodyLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(l_additionalServices, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(135, 135, 135)
                        .addComponent(l_flightCostSummary))
                    .addGroup(p_bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(p_bodyLayout.createSequentialGroup()
                            .addGroup(p_bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(chb_pwdAssistance)
                                .addComponent(chb_priorityCheckinAndBoarding)
                                .addComponent(chb_inflightWifiAccess)
                                .addComponent(chb_extraSnacksAndDrinks)
                                .addComponent(chb_extraBaggage))
                            .addGap(53, 53, 53)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, p_bodyLayout.createSequentialGroup()
                            .addComponent(l_emailAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tf_emailAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(l_passportNumber, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, p_bodyLayout.createSequentialGroup()
                            .addGroup(p_bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(l_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(l_address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(l_nationality, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(1, 1, 1)
                            .addGroup(p_bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(p_bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(p_bodyLayout.createSequentialGroup()
                                        .addComponent(tf_name, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(l_age, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tf_age, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(p_bodyLayout.createSequentialGroup()
                                        .addGroup(p_bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(cb_nationality, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tf_passportNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(p_bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(p_bodyLayout.createSequentialGroup()
                                                .addComponent(l_contactNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(tf_contactNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(p_bodyLayout.createSequentialGroup()
                                                .addComponent(l_gender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(rb_male)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(rb_female, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(rb_others)))))
                                .addComponent(tf_address, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, p_bodyLayout.createSequentialGroup()
                            .addComponent(l_dateOfFlight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(1, 1, 1)
                            .addGroup(p_bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(p_bodyLayout.createSequentialGroup()
                                    .addGap(10, 10, 10)
                                    .addComponent(l_departureAndDestination)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cb_departureAndDestination, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(p_bodyLayout.createSequentialGroup()
                                    .addComponent(tf_dateofflight, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(l_mealPreference, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cb_mealPreference, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addComponent(l_addtionalNotes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        p_bodyLayout.setVerticalGroup(
            p_bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_bodyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(p_bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(l_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(l_age, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_age, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(p_bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(l_address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(p_bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(l_nationality, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cb_nationality, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(p_bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, p_bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rb_male, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rb_female, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rb_others))
                        .addComponent(l_gender, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(p_bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(l_passportNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(l_contactNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_passportNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_contactNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(p_bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(l_emailAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_emailAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(p_bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(l_dateOfFlight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_dateofflight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(l_mealPreference, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cb_mealPreference, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(p_bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(l_departureAndDestination)
                    .addComponent(cb_departureAndDestination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(p_bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(p_bodyLayout.createSequentialGroup()
                        .addComponent(l_additionalServices, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(chb_pwdAssistance)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chb_extraBaggage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chb_extraSnacksAndDrinks)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chb_inflightWifiAccess)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chb_priorityCheckinAndBoarding))
                    .addGroup(p_bodyLayout.createSequentialGroup()
                        .addComponent(l_flightCostSummary)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(l_addtionalNotes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        p_main.add(p_body, java.awt.BorderLayout.CENTER);

        b_clear.setText("Clear");
        b_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_clearActionPerformed(evt);
            }
        });
        p_footer.add(b_clear);

        b_confirm.setText("Confirm");
        b_confirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_confirmActionPerformed(evt);
            }
        });
        p_footer.add(b_confirm);

        p_main.add(p_footer, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(p_main, java.awt.BorderLayout.CENTER);

        p_seats.setBorder(javax.swing.BorderFactory.createTitledBorder("Seats"));
        p_seats.setMinimumSize(new java.awt.Dimension(200, 2));
        p_seats.setPreferredSize(new java.awt.Dimension(200, 600));
        p_seats.setLayout(new java.awt.GridLayout(4, 2));

        p_seat_1a.setLayout(new java.awt.BorderLayout());

        b_seat_1a.setBackground(new java.awt.Color(153, 255, 153));
        b_seat_1a.setText("1A");
        b_seat_1a.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b_seat_1aMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                b_seat_1aMouseExited(evt);
            }
        });
        b_seat_1a.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_seat_1aActionPerformed(evt);
            }
        });
        p_seat_1a.add(b_seat_1a, java.awt.BorderLayout.CENTER);

        p_seats.add(p_seat_1a);

        p_seat_1b.setLayout(new java.awt.BorderLayout());

        b_seat_1b.setBackground(new java.awt.Color(153, 255, 153));
        b_seat_1b.setText("1B");
        b_seat_1b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b_seat_1bMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                b_seat_1bMouseExited(evt);
            }
        });
        b_seat_1b.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_seat_1bActionPerformed(evt);
            }
        });
        p_seat_1b.add(b_seat_1b, java.awt.BorderLayout.CENTER);

        p_seats.add(p_seat_1b);

        p_seat_2a.setLayout(new java.awt.BorderLayout());

        b_seat_2a.setBackground(new java.awt.Color(153, 255, 153));
        b_seat_2a.setText("2A");
        b_seat_2a.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b_seat_2aMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                b_seat_2aMouseExited(evt);
            }
        });
        b_seat_2a.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_seat_2aActionPerformed(evt);
            }
        });
        p_seat_2a.add(b_seat_2a, java.awt.BorderLayout.CENTER);

        p_seats.add(p_seat_2a);

        p_seat_2b.setLayout(new java.awt.BorderLayout());

        b_seat_2b.setBackground(new java.awt.Color(153, 255, 153));
        b_seat_2b.setText("2B");
        b_seat_2b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b_seat_2bMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                b_seat_2bMouseExited(evt);
            }
        });
        b_seat_2b.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_seat_2bActionPerformed(evt);
            }
        });
        p_seat_2b.add(b_seat_2b, java.awt.BorderLayout.CENTER);

        p_seats.add(p_seat_2b);

        p_seat_3a.setLayout(new java.awt.BorderLayout());

        b_seat_3a.setBackground(new java.awt.Color(153, 255, 153));
        b_seat_3a.setText("3A");
        b_seat_3a.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b_seat_3aMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                b_seat_3aMouseExited(evt);
            }
        });
        b_seat_3a.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_seat_3aActionPerformed(evt);
            }
        });
        p_seat_3a.add(b_seat_3a, java.awt.BorderLayout.CENTER);

        p_seats.add(p_seat_3a);

        p_seat_3b.setLayout(new java.awt.BorderLayout());

        b_seat_3b.setBackground(new java.awt.Color(153, 255, 153));
        b_seat_3b.setText("3B");
        b_seat_3b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b_seat_3bMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                b_seat_3bMouseExited(evt);
            }
        });
        b_seat_3b.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_seat_3bActionPerformed(evt);
            }
        });
        p_seat_3b.add(b_seat_3b, java.awt.BorderLayout.CENTER);

        p_seats.add(p_seat_3b);

        p_seat_4a.setLayout(new java.awt.BorderLayout());

        b_seat_4a.setBackground(new java.awt.Color(153, 255, 153));
        b_seat_4a.setText("4A");
        b_seat_4a.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b_seat_4aMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                b_seat_4aMouseExited(evt);
            }
        });
        b_seat_4a.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_seat_4aActionPerformed(evt);
            }
        });
        p_seat_4a.add(b_seat_4a, java.awt.BorderLayout.CENTER);

        p_seats.add(p_seat_4a);

        p_seat_4b.setLayout(new java.awt.BorderLayout());

        b_seat_4b.setBackground(new java.awt.Color(153, 255, 153));
        b_seat_4b.setText("4B");
        b_seat_4b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b_seat_4bMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                b_seat_4bMouseExited(evt);
            }
        });
        b_seat_4b.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_seat_4bActionPerformed(evt);
            }
        });
        p_seat_4b.add(b_seat_4b, java.awt.BorderLayout.CENTER);

        p_seats.add(p_seat_4b);

        getContentPane().add(p_seats, java.awt.BorderLayout.LINE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void comB_flightNumberItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comB_flightNumberItemStateChanged
        switch(comB_flightNumber.getSelectedItem().toString()) {
            case "F1":
                ClearSelectedSeats(F1SeatList); // Just an extra measure--Better safe than sorry
                UpdateSeatList(F1SeatList);
                break;
            case "F2":
                ClearSelectedSeats(F1SeatList);
                UpdateSeatList(F2SeatList);
                break;
        }
    }//GEN-LAST:event_comB_flightNumberItemStateChanged

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:\
        UpdateSeatList(F1SeatList);
    }//GEN-LAST:event_formWindowOpened

    private void b_seat_1aActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_seat_1aActionPerformed
        // TODO add your handling code here:
        seatButtonClicked(b_seat_1a, 0, 0);
    }//GEN-LAST:event_b_seat_1aActionPerformed

    private void b_seat_1bActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_seat_1bActionPerformed
        // TODO add your handling code here:
       seatButtonClicked(b_seat_1b, 0, 1);
    }//GEN-LAST:event_b_seat_1bActionPerformed

    private void b_seat_2aActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_seat_2aActionPerformed
        // TODO add your handling code here:
       seatButtonClicked(b_seat_2a, 1, 0);
    }//GEN-LAST:event_b_seat_2aActionPerformed

    private void b_seat_2bActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_seat_2bActionPerformed
        // TODO add your handling code here:
         seatButtonClicked(b_seat_2b, 1, 1);
    }//GEN-LAST:event_b_seat_2bActionPerformed

    private void b_seat_3aActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_seat_3aActionPerformed
        // TODO add your handling code here:
         seatButtonClicked(b_seat_3a, 2, 0);
    }//GEN-LAST:event_b_seat_3aActionPerformed

    private void b_seat_3bActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_seat_3bActionPerformed
        // TODO add your handling code here:
         seatButtonClicked(b_seat_3b, 2, 1);
    }//GEN-LAST:event_b_seat_3bActionPerformed

    private void b_seat_4aActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_seat_4aActionPerformed
        // TODO add your handling code here:
         seatButtonClicked(b_seat_4a, 3, 0);
    }//GEN-LAST:event_b_seat_4aActionPerformed

    private void b_seat_4bActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_seat_4bActionPerformed
        // TODO add your handling code here:
         seatButtonClicked(b_seat_4b, 3, 1);
    }//GEN-LAST:event_b_seat_4bActionPerformed

    private void b_confirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_confirmActionPerformed
 int result = JOptionPane.showConfirmDialog(this, "Do you want to confirm this booking?", "Confirmation", JOptionPane.YES_NO_OPTION);
if (result == JOptionPane.YES_OPTION) {
    // Get values from input fields
    String name = tf_name.getText();
    String age = tf_age.getText();
    String address = tf_address.getText();
    String nationality = (String) cb_nationality.getSelectedItem();
    String gender = rb_male.isSelected() ? "Male" : rb_female.isSelected() ? "Female" : rb_others.isSelected() ? "Others" : "Not Selected";
    String passportNumber = tf_passportNumber.getText();
    String contactNumber = tf_contactNumber.getText();
    String emailAddress = tf_emailAddress.getText();
    String dateOfFlight = tf_dateofflight.getText();
    String mealPreference = (String) cb_mealPreference.getSelectedItem();
    String departureAndDestination = (String) cb_departureAndDestination.getSelectedItem();
    currentlySelectedSeat.setBackground(Color.red);
    
    // Get additional services
    String additionalServices = "";
    if (chb_pwdAssistance.isSelected()) additionalServices += "PWD Assistance, ";
    if (chb_extraBaggage.isSelected()) additionalServices += "Extra Baggage, ";
    if (chb_extraSnacksAndDrinks.isSelected()) additionalServices += "Extra Snacks & Drinks, ";
    if (chb_inflightWifiAccess.isSelected()) additionalServices += "In-Flight Wi-Fi Access, ";
    if (chb_priorityCheckinAndBoarding.isSelected()) additionalServices += "Priority Check-in & Boarding, ";

    if (!additionalServices.isEmpty()) {
        additionalServices = additionalServices.substring(0, additionalServices.length() - 2);
    } else {
        additionalServices = "None";
    }

    // Get selected seat
    String selectedSeat = (currentlySelectedSeat != null) ? currentlySelectedSeat.getText() : "None";
    // Where which flight number the seat occupation belongs to will depend on this
    switch(comB_flightNumber.getSelectedItem().toString()) {
        case "F1":
            F1SeatList[currentlySelectedSeatIndex[0]][currentlySelectedSeatIndex[1]] = "occupied";
            UpdateSeatList(F1SeatList);
            break;
        case "F2":
            F2SeatList[currentlySelectedSeatIndex[0]][currentlySelectedSeatIndex[1]] = "occupied";
            UpdateSeatList(F2SeatList);
            break;
    }

    // Display confirmation message
    String message = "Flight Confirmed!\n"
            + "Name: " + name + "\n"
            + "Age: " + age + "\n"
            + "Address: " + address + "\n"
            + "Nationality: " + nationality + "\n"
            + "Gender: " + gender + "\n"
            + "Passport Number: " + passportNumber + "\n"
            + "Contact Number: " + contactNumber + "\n"
            + "Email Address: " + emailAddress + "\n"
            + "Date of Flight: " + dateOfFlight + "\n"
            + "Meal Preference: " + mealPreference + "\n"
            + "Departure & Destination: " + departureAndDestination + "\n"
            + "Selected Seat: " + selectedSeat + "\n"
            + "Additional Services: " + additionalServices;

    JOptionPane.showMessageDialog(this, message, "Booking Confirmation", JOptionPane.INFORMATION_MESSAGE);
}
    }//GEN-LAST:event_b_confirmActionPerformed

    private void b_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_clearActionPerformed
int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to clear all fields?", "Confirmation", JOptionPane.YES_NO_OPTION);
if (result == JOptionPane.YES_OPTION) {
    // Clear all 
    tf_name.setText("");
    tf_age.setText("");
    tf_address.setText("");
    cb_nationality.setSelectedIndex(0);
    rb_male.setSelected(false);
    rb_female.setSelected(false);
    rb_others.setSelected(false);
    tf_passportNumber.setText("");
    tf_contactNumber.setText("");
    tf_emailAddress.setText("");
    tf_dateofflight.setText("");
    cb_mealPreference.setSelectedIndex(0);
    cb_departureAndDestination.setSelectedIndex(0);
    chb_pwdAssistance.setSelected(false);
    chb_extraBaggage.setSelected(false);
    chb_extraSnacksAndDrinks.setSelected(false);
    chb_inflightWifiAccess.setSelected(false);
    chb_priorityCheckinAndBoarding.setSelected(false);
    
    // Reset 
    if (currentlySelectedSeat != null) {
        currentlySelectedSeat.setBackground(Color.GREEN); // Reset color
        currentlySelectedSeat = null;
    }
    for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 2; j++) {
            F1SeatList[i][j] = "vacant";
        }
    }
 updateFlightCostSummary();
    // Refresh 
    UpdateSeatList(F1SeatList);
}
    }//GEN-LAST:event_b_clearActionPerformed

    private void tf_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_nameActionPerformed
    checkFormCompletion();    // TODO add your handling code here:
        
    }//GEN-LAST:event_tf_nameActionPerformed

    private void tf_ageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_ageActionPerformed
   checkFormCompletion();     // TODO add your handling code here:
    }//GEN-LAST:event_tf_ageActionPerformed

    private void tf_addressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_addressActionPerformed
   checkFormCompletion();     // TODO add your handling code here:
    }//GEN-LAST:event_tf_addressActionPerformed

    private void cb_nationalityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_nationalityActionPerformed
   checkFormCompletion();     // TODO add your handling code here:
    }//GEN-LAST:event_cb_nationalityActionPerformed

    private void rb_maleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_maleActionPerformed
    checkFormCompletion();        // TODO add your handling code here:
        
    }//GEN-LAST:event_rb_maleActionPerformed

    private void rb_femaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_femaleActionPerformed
    checkFormCompletion();    // TODO add your handling code here:
    }//GEN-LAST:event_rb_femaleActionPerformed

    private void rb_othersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_othersActionPerformed
     checkFormCompletion();   // TODO add your handling code here:
    }//GEN-LAST:event_rb_othersActionPerformed

    private void tf_passportNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_passportNumberActionPerformed
     checkFormCompletion();   // TODO add your handling code here:
    }//GEN-LAST:event_tf_passportNumberActionPerformed

    private void tf_contactNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_contactNumberActionPerformed
     checkFormCompletion();   // TODO add your handling code here:
    }//GEN-LAST:event_tf_contactNumberActionPerformed

    private void tf_emailAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_emailAddressActionPerformed
      checkFormCompletion();  // TODO add your handling code here:
    }//GEN-LAST:event_tf_emailAddressActionPerformed

    private void tf_dateofflightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_dateofflightActionPerformed
       checkFormCompletion(); // TODO add your handling code here:
    }//GEN-LAST:event_tf_dateofflightActionPerformed

    private void cb_mealPreferenceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_mealPreferenceActionPerformed
      checkFormCompletion();  // TODO add your handling code here:
    }//GEN-LAST:event_cb_mealPreferenceActionPerformed

    private void cb_departureAndDestinationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_departureAndDestinationActionPerformed
     updateFlightCostSummary();
        checkFormCompletion();  // TODO add your handling code here:
    }//GEN-LAST:event_cb_departureAndDestinationActionPerformed

    private void chb_pwdAssistanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chb_pwdAssistanceActionPerformed
       updateFlightCostSummary();
       checkFormCompletion();  // TODO add your handling code here:
    }//GEN-LAST:event_chb_pwdAssistanceActionPerformed

    private void chb_extraBaggageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chb_extraBaggageActionPerformed
       checkFormCompletion();
        updateFlightCostSummary();// TODO add your handling code here:
    }//GEN-LAST:event_chb_extraBaggageActionPerformed

    private void chb_extraSnacksAndDrinksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chb_extraSnacksAndDrinksActionPerformed
    checkFormCompletion();   
     updateFlightCostSummary();// TODO add your handling code here:
    }//GEN-LAST:event_chb_extraSnacksAndDrinksActionPerformed

    private void chb_inflightWifiAccessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chb_inflightWifiAccessActionPerformed
     checkFormCompletion();   
 updateFlightCostSummary();// TODO add your handling code here:
    }//GEN-LAST:event_chb_inflightWifiAccessActionPerformed

    private void chb_priorityCheckinAndBoardingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chb_priorityCheckinAndBoardingActionPerformed
     checkFormCompletion();
      updateFlightCostSummary();// TODO add your handling code here:
    }//GEN-LAST:event_chb_priorityCheckinAndBoardingActionPerformed

    private void rb_maleItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rb_maleItemStateChanged
//         if (rb_male.isSelected()) {
//        rb_female.setEnabled(false);
//        rb_others.setEnabled(false);
//    } else {
//        rb_female.setEnabled(true);
//        rb_others.setEnabled(true);
//    }
    // lol no need to do this
    }//GEN-LAST:event_rb_maleItemStateChanged

    private void rb_femaleItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rb_femaleItemStateChanged
//        // TODO add your handling code here:
//         if (rb_female.isSelected()) {
//        rb_male.setEnabled(false);
//        rb_others.setEnabled(false);
//    } else {
//        rb_male.setEnabled(true);
//        rb_others.setEnabled(true);
//    }
    }//GEN-LAST:event_rb_femaleItemStateChanged

    private void rb_othersItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rb_othersItemStateChanged
//        // TODO add your handling code here:
//        if (rb_others.isSelected()) {
//        rb_male.setEnabled(false);
//        rb_female.setEnabled(false);
//    } else {
//        rb_male.setEnabled(true);
//        rb_female.setEnabled(true);
//    }
    }//GEN-LAST:event_rb_othersItemStateChanged

    private void tf_passportNumberKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_passportNumberKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
    if (!Character.isDigit(c)) {
        evt.consume(); 
        Toolkit.getDefaultToolkit().beep();
    }
    }//GEN-LAST:event_tf_passportNumberKeyTyped

    private void tf_contactNumberKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_contactNumberKeyTyped
        // TODO add your handling code here:
         char c = evt.getKeyChar();
    if (!Character.isDigit(c)) {
        evt.consume(); 
        Toolkit.getDefaultToolkit().beep(); 
    }
    }//GEN-LAST:event_tf_contactNumberKeyTyped

    private void tf_ageKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_ageKeyTyped
 char c = evt.getKeyChar();
    if (!Character.isDigit(c)) {
        evt.consume(); 
        Toolkit.getDefaultToolkit().beep();
    }        // TODO add your handling code here:
    }//GEN-LAST:event_tf_ageKeyTyped

    private void b_seat_1aMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b_seat_1aMouseEntered
        if (b_seat_1a.getBackground() == Color.GREEN) {
        b_seat_1a.setBackground(new Color(144, 238, 144));
    } 
        seatMouseEntered(b_seat_1a);
        // TODO add your handling code here:
    }//GEN-LAST:event_b_seat_1aMouseEntered

    private void b_seat_1aMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b_seat_1aMouseExited
 if (b_seat_1a.getBackground().equals(new Color(144, 238, 144))) {
        b_seat_1a.setBackground(Color.GREEN); // Reset to original
    }        // TODO add your handling code here:
    }//GEN-LAST:event_b_seat_1aMouseExited

    private void b_seat_1bMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b_seat_1bMouseEntered
      
        seatMouseEntered(b_seat_1b);// TODO add your handling code here:
    }//GEN-LAST:event_b_seat_1bMouseEntered

    private void b_seat_2aMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b_seat_2aMouseEntered
        seatMouseEntered(b_seat_2a);// TODO add your handling code here:
    }//GEN-LAST:event_b_seat_2aMouseEntered

    private void b_seat_3aMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b_seat_3aMouseEntered
        seatMouseEntered(b_seat_3a);// TODO add your handling code here:
    }//GEN-LAST:event_b_seat_3aMouseEntered

    private void b_seat_4aMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b_seat_4aMouseEntered
      seatMouseEntered(b_seat_4a);  // TODO add your handling code here:
    }//GEN-LAST:event_b_seat_4aMouseEntered

    private void b_seat_2bMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b_seat_2bMouseEntered
      seatMouseEntered(b_seat_2b);  // TODO add your handling code here:
    }//GEN-LAST:event_b_seat_2bMouseEntered

    private void b_seat_3bMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b_seat_3bMouseEntered
seatMouseEntered(b_seat_3b);        // TODO add your handling code here:
    }//GEN-LAST:event_b_seat_3bMouseEntered

    private void b_seat_4bMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b_seat_4bMouseEntered
seatMouseEntered(b_seat_4b);        // TODO add your handling code here:
    }//GEN-LAST:event_b_seat_4bMouseEntered

    private void b_seat_1bMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b_seat_1bMouseExited
seatMouseExited(b_seat_1b);        // TODO add your handling code here:
    }//GEN-LAST:event_b_seat_1bMouseExited

    private void b_seat_2bMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b_seat_2bMouseExited
seatMouseExited(b_seat_2b);                // TODO add your handling code here:
    }//GEN-LAST:event_b_seat_2bMouseExited

    private void b_seat_3bMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b_seat_3bMouseExited
seatMouseExited(b_seat_3b);                // TODO add your handling code here:
    }//GEN-LAST:event_b_seat_3bMouseExited

    private void b_seat_4bMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b_seat_4bMouseExited
seatMouseExited(b_seat_4b);                // TODO add your handling code here:
    }//GEN-LAST:event_b_seat_4bMouseExited

    private void b_seat_2aMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b_seat_2aMouseExited
seatMouseExited(b_seat_2a);                // TODO add your handling code here:
    }//GEN-LAST:event_b_seat_2aMouseExited

    private void b_seat_3aMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b_seat_3aMouseExited
seatMouseExited(b_seat_3a);           // TODO add your handling code here:
    }//GEN-LAST:event_b_seat_3aMouseExited

    private void b_seat_4aMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b_seat_4aMouseExited
seatMouseExited(b_seat_4a);           // TODO add your handling code here:
    }//GEN-LAST:event_b_seat_4aMouseExited

    /**
     * @param args the command line arguments
     */
    
     private void customizeSeatButtons() {
    b_seat_1a.setOpaque(true);
    b_seat_1a.setContentAreaFilled(true);
    b_seat_1a.setBorderPainted(true);
    b_seat_1a.setFocusPainted(false);
    b_seat_1a.setBackground(java.awt.Color.GREEN);
    
    b_seat_1b.setOpaque(true);
    b_seat_1b.setContentAreaFilled(true);
    b_seat_1b.setBorderPainted(true);
    b_seat_1b.setFocusPainted(false);
    b_seat_1b.setBackground(java.awt.Color.GREEN);
    
    b_seat_2a.setOpaque(true);
    b_seat_2a.setContentAreaFilled(true);
    b_seat_2a.setBorderPainted(true);
    b_seat_2a.setFocusPainted(false);
    b_seat_2a.setBackground(java.awt.Color.GREEN);
    
    b_seat_2b.setOpaque(true);
    b_seat_2b.setContentAreaFilled(true);
    b_seat_2b.setBorderPainted(true);
    b_seat_2b.setFocusPainted(false);
    b_seat_2b.setBackground(java.awt.Color.GREEN);
    
    b_seat_3a.setOpaque(true);
    b_seat_3a.setContentAreaFilled(true);
    b_seat_3a.setBorderPainted(true);
    b_seat_3a.setFocusPainted(false);
    b_seat_3a.setBackground(java.awt.Color.GREEN);
    
    b_seat_3b.setOpaque(true);
    b_seat_3b.setContentAreaFilled(true);
    b_seat_3b.setBorderPainted(true);
    b_seat_3b.setFocusPainted(false);
    b_seat_3b.setBackground(java.awt.Color.GREEN);
    
    b_seat_4a.setOpaque(true);
    b_seat_4a.setContentAreaFilled(true);
    b_seat_4a.setBorderPainted(true);
    b_seat_4a.setFocusPainted(false);
    b_seat_4a.setBackground(java.awt.Color.GREEN);
    
    b_seat_4b.setOpaque(true);
    b_seat_4b.setContentAreaFilled(true);
    b_seat_4b.setBorderPainted(true);
    b_seat_4b.setFocusPainted(false);
    b_seat_4b.setBackground(java.awt.Color.GREEN);
         
     }
          private void seatButtonClicked(JButton seatButton) {
              
    
    
        // If the seat is RED, do nothing (occupied)
        if (seatButton.getBackground().equals(Color.RED)) {
            return;
        }

        // If this seat is already selected (yellow), deselect it
        if (seatButton.getBackground().equals(Color.YELLOW)) {
            seatButton.setBackground(Color.GREEN);
            currentlySelectedSeat = null;
        } 
        // Otherwise, seat is green (vacant)
        else {
            // If another seat was selected, turn it green
            if (currentlySelectedSeat != null) {
               currentlySelectedSeat.setBackground(Color.GREEN);
            }
            // Mark this seat as selected
            seatButton.setBackground(Color.YELLOW);
            currentlySelectedSeat = seatButton;
        }
        
    }
          
          
    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b_clear;
    private javax.swing.JButton b_confirm;
    private javax.swing.JButton b_seat_1a;
    private javax.swing.JButton b_seat_1b;
    private javax.swing.JButton b_seat_2a;
    private javax.swing.JButton b_seat_2b;
    private javax.swing.JButton b_seat_3a;
    private javax.swing.JButton b_seat_3b;
    private javax.swing.JButton b_seat_4a;
    private javax.swing.JButton b_seat_4b;
    private javax.swing.JComboBox<String> cb_departureAndDestination;
    private javax.swing.JComboBox<String> cb_mealPreference;
    private javax.swing.JComboBox<String> cb_nationality;
    private javax.swing.JCheckBox chb_extraBaggage;
    private javax.swing.JCheckBox chb_extraSnacksAndDrinks;
    private javax.swing.JCheckBox chb_inflightWifiAccess;
    private javax.swing.JCheckBox chb_priorityCheckinAndBoarding;
    private javax.swing.JCheckBox chb_pwdAssistance;
    private javax.swing.JComboBox<String> comB_flightNumber;
    private javax.swing.ButtonGroup gender;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private java.awt.Label l_additionalServices;
    private java.awt.Label l_address;
    private javax.swing.JLabel l_addtionalNotes;
    private java.awt.Label l_age;
    private java.awt.Label l_contactNumber;
    private java.awt.Label l_dateOfFlight;
    private javax.swing.JLabel l_departureAndDestination;
    private java.awt.Label l_emailAddress;
    private javax.swing.JLabel l_flightCostSummary;
    private javax.swing.JLabel l_flightNumber;
    private java.awt.Label l_gender;
    private java.awt.Label l_mealPreference;
    private java.awt.Label l_name;
    private java.awt.Label l_nationality;
    private java.awt.Label l_passportNumber;
    private javax.swing.JPanel p_body;
    private javax.swing.JPanel p_footer;
    private javax.swing.JPanel p_header;
    private javax.swing.JPanel p_main;
    private javax.swing.JPanel p_seat_1a;
    private javax.swing.JPanel p_seat_1b;
    private javax.swing.JPanel p_seat_2a;
    private javax.swing.JPanel p_seat_2b;
    private javax.swing.JPanel p_seat_3a;
    private javax.swing.JPanel p_seat_3b;
    private javax.swing.JPanel p_seat_4a;
    private javax.swing.JPanel p_seat_4b;
    private javax.swing.JPanel p_seats;
    private javax.swing.JRadioButton rb_female;
    private javax.swing.JRadioButton rb_male;
    private javax.swing.JRadioButton rb_others;
    private javax.swing.JTextArea ta_additionalNotes;
    private javax.swing.JTextArea ta_flightCostSummary;
    private java.awt.TextField tf_address;
    private java.awt.TextField tf_age;
    private java.awt.TextField tf_contactNumber;
    private java.awt.TextField tf_dateofflight;
    private java.awt.TextField tf_emailAddress;
    private java.awt.TextField tf_name;
    private java.awt.TextField tf_passportNumber;
    // End of variables declaration//GEN-END:variables
}
