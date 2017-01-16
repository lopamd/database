/**
 * @author Lopamudra Muduli <lopamudra.muduli@utdallas.edu>
 * 
 */

package contactmanager;

import java.awt.HeadlessException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

public class ContactManagerSwing extends javax.swing.JFrame {

    public boolean databaseConn;
    private java.sql.Connection conn;
    private java.sql.CallableStatement stmt;
    private java.sql.ResultSet rset;
    private int selectedContactID;
    private Matcher matcher;
    private Pattern pattern;
    private HashMap hashmap = new HashMap();
    SimpleDateFormat formater = new SimpleDateFormat("mm/dd/yyyy");
    
    /** Regex pattern for E-mail,Phone and Zip field validation */
    private static final String EMAIL_PATTERN
            = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PHONE_PATTERN = "^[0-9]{10}$";
    private static final String ZIP_PATTERN = "^\\d{5}([\\-]?\\d{4})?$";

	/**
	 * isEmptyFieldData() - Check if all the fields are empty.
	 * 
	 * @return - True if empty, False otherwise 
	 */
    private boolean isEmptyFieldData() {
        return (fname.getText().trim().isEmpty()
            && minit.getText().trim().isEmpty()
            && lname.getText().trim().isEmpty()
            && gender.getSelectedItem().toString().isEmpty()
            && (dob.getDate()== null)
            && address.getText().trim().isEmpty()
            && city.getText().trim().isEmpty()
            && state.getText().trim().isEmpty()
            && postcode.getText().trim().isEmpty()
            &&(meetDate.getDate()== null)
            && phone.getText().trim().isEmpty()
            && EmailID.getText().trim().isEmpty()
            && groupidcombo.getSelectedItem().toString().isEmpty());
    }
    
	/**
	 * cleardata() - Clears all the fields after insert/update/delete operations.
	 * 
	 * @return - True if empty, False otherwise.
	 */
    private void cleardata() {
        fname.setText("");
        minit.setText("");
        lname.setText("");
        gender.setSelectedItem("");
        dob.setDate(null);
        address.setText("");
        city.setText("");
        state.setText("");
        postcode.setText("");
        meetDate.setDate(null);
        phone.setText("");
        EmailID.setText("");
        groupidcombo.setSelectedItem("");
    }

	/**
	 * emailvalidate() -  Validate the email address using the regex pattern.
	 * @param - email
	 *
	 * @return - True if matches, False otherwise.
	 */    
    public boolean emailvalidate(final String email) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

	/**
	 * phonevalidate() - Validate the Phone number using the regex pattern.
	 * @param phn - phone number
	 * 
	 * @return - True if matches, False otherwise.
	 */
    public boolean phonevalidate(final String phn) {
        pattern = Pattern.compile(PHONE_PATTERN);
        matcher = pattern.matcher(phn);
        return matcher.matches();
    }

	/**
	 * zipvalidate() - Validate the Zip code using the regex pattern.
	 * @param zip - zip code
	 *
	 * @return - True if matches, False otherwise.
	 */    
    public boolean zipvalidate(final String zip){
        pattern = Pattern.compile(ZIP_PATTERN);
        matcher = pattern.matcher(zip);
        return matcher.matches();    
    }

	/**
	 * getData() - Loads the the database contact data loaded when the program runs.
	 * 
	 * @return - None.
	 */
    private void getData() {
        try {
            conn = (java.sql.Connection) contactmanager.JDBCconn.sqlconn();
            stmt = conn.prepareCall("{ call GetDetailsInfo()}");
            rset = stmt.executeQuery();

            TableModel tm = DbUtils.resultSetToTableModel(rset);
            for (int i = 0; i < tm.getRowCount(); i++) {
                for (int j = 0; j < tm.getColumnCount(); j++) {
                    hashmap.put(i, tm.getValueAt(i, 13));
                }
            }
            JContactTable.setModel(tm);

            TableColumnModel cTableM = JContactTable.getColumnModel();
            JContactTable.removeColumn(cTableM.getColumn(13));
            JContactTable.setDefaultEditor(Object.class, null);
            cTableM.getColumn(0).setHeaderValue("Firstname");
            cTableM.getColumn(1).setHeaderValue("minit");
            cTableM.getColumn(2).setHeaderValue("Lastname");
            cTableM.getColumn(3).setHeaderValue("Gender");
            cTableM.getColumn(4).setHeaderValue("DOB");
            cTableM.getColumn(5).setHeaderValue("Street");
            cTableM.getColumn(6).setHeaderValue("City");
            cTableM.getColumn(7).setHeaderValue("State");
            cTableM.getColumn(8).setHeaderValue("PostCode");
            cTableM.getColumn(9).setHeaderValue("PhoneNumber");
            cTableM.getColumn(10).setHeaderValue("Email");
            cTableM.getColumn(11).setHeaderValue("MeetingDate");
            cTableM.getColumn(12).setHeaderValue("ContactID");            
        } catch (SQLException e) {
            System.out.println("Exception occured " + e.getMessage());
        }
    }
    
    /**
     * getValueAt - retrieve the value at each cell in JTable 
     * @param rIndex - row index of JTable
     * @param cIndex - column index of JTable
     * @return 
     */
    private String getValueAt(int rIndex, int cIndex) {
        Object val = (JContactTable.getModel().getValueAt(rIndex, cIndex));
        if (val != null) {
            return val.toString();
        } else {
            return "";
        }
    }

    public ContactManagerSwing() {
        initComponents();
        getData();
        databaseConn = true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lname = new javax.swing.JTextField();
        minit = new javax.swing.JTextField();
        fname = new javax.swing.JTextField();
        addButton = new javax.swing.JButton();
        DelButton = new javax.swing.JButton();
        updateButton = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        gender = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        dob = new org.jdesktop.swingx.JXDatePicker();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        address = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        city = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        state = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        postcode = new javax.swing.JTextField();
        Communication = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        phone = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        EmailID = new javax.swing.JTextField();
        groupid = new javax.swing.JLabel();
        groupidcombo = new javax.swing.JComboBox<>();
        add = new javax.swing.JButton();
        update = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        meetDate = new org.jdesktop.swingx.JXDatePicker();
        jScrollPane1 = new javax.swing.JScrollPane();
        JContactTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Contact Details"));

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel2.setText("First Name:");

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel3.setText("Minit:");

        jLabel4.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel4.setText("Last Name:");

        addButton.setFont(new java.awt.Font("Lucida Grande", 3, 14)); // NOI18N
        addButton.setText("Add");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        DelButton.setFont(new java.awt.Font("Lucida Grande", 3, 14)); // NOI18N
        DelButton.setText("Delete");
        DelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DelButtonActionPerformed(evt);
            }
        });

        updateButton.setFont(new java.awt.Font("Lucida Grande", 3, 14)); // NOI18N
        updateButton.setText("Update");
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel9.setText("Gender:");

        gender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "","M", "F", "T" }));

        jLabel10.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel10.setText("Date of Birth:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(addButton)
                .addGap(52, 52, 52)
                .addComponent(DelButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 122, Short.MAX_VALUE)
                .addComponent(updateButton)
                .addGap(38, 38, 38))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dob, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(gender, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fname, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                            .addComponent(minit)
                            .addComponent(lname))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(fname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(minit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(gender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(dob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(111, 111, 111)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addButton)
                    .addComponent(DelButton)
                    .addComponent(updateButton))
                .addGap(303, 303, 303))
        );

        jLabel1.setFont(new java.awt.Font("American Typewriter", 3, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Contact Manager ");
        jLabel1.setSize(new java.awt.Dimension(55, 16));

        jPanel3.setBackground(new java.awt.Color(204, 204, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Address"));

        jLabel6.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel6.setText("Street:");

        jLabel7.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel7.setText("City:");

        jLabel11.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel11.setText("State:");

        jLabel12.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel12.setText("Zipcode:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(47, 47, 47)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(address, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(city, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(state, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                            .addComponent(postcode))))
                .addContainerGap(113, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(city, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(state, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(postcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        Communication.setBackground(new java.awt.Color(204, 204, 255));
        Communication.setBorder(javax.swing.BorderFactory.createTitledBorder("Communication"));

        jLabel5.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel5.setText("Phone Number:");

        jLabel8.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel8.setText("E-mail ID:");

        groupid.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        groupid.setText("Group_ID:");

        groupidcombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "","1", "2", "3", "4", "5" }));
        groupidcombo.setToolTipText("");

        add.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        add.setText("ADD");
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        update.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        update.setText("UPDATE");
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        delete.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        delete.setText("DELETE");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout CommunicationLayout = new javax.swing.GroupLayout(Communication);
        Communication.setLayout(CommunicationLayout);
        CommunicationLayout.setHorizontalGroup(
            CommunicationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CommunicationLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(add)
                .addGap(83, 83, 83)
                .addComponent(update)
                .addGap(76, 76, 76)
                .addComponent(delete)
                .addGap(351, 351, 351))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CommunicationLayout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(phone, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(EmailID, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76)
                .addComponent(groupid)
                .addGap(18, 18, 18)
                .addComponent(groupidcombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        CommunicationLayout.setVerticalGroup(
            CommunicationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CommunicationLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(CommunicationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(phone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(add)
                .addGap(299, 299, 299))
            .addGroup(CommunicationLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(CommunicationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(EmailID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(groupid)
                    .addComponent(groupidcombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(CommunicationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(delete)
                    .addComponent(update))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel13.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel13.setText("Meeting Date:");

        JContactTable.setAutoCreateRowSorter(true);
        JContactTable.setBackground(new java.awt.Color(255, 204, 204));
        JContactTable.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        JContactTable.setFont(new java.awt.Font("Lucida Grande", 0, 13)); // NOI18N
        JContactTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "FirstName", "Minit", "LastName", "Gender", "DOB", "Street", "City", "State", "PostCode", "PhoneNumber", "Email", "Meeting Date", "ContactGroupID"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        JContactTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        JContactTable.setColumnSelectionAllowed(true);
        JContactTable.setGridColor(new java.awt.Color(0, 153, 153));
        JContactTable.setSelectionBackground(new java.awt.Color(102, 102, 255));
        JContactTable.setShowGrid(true);
        JContactTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JContactTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(JContactTable);
        JContactTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Communication, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(meetDate, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1033, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(21, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(442, 442, 442)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(meetDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Communication, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 503, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1060, 880));

        pack();
    }// </editor-fold>//GEN-END:initComponents

	/**
	 * This function handles the add button click handler. It retrieves all the data from the UI and invokes
	 * the stored procedure to insert into the database.
	 * addButtonActionPerformed() - Add button click action handler.
	 * @evt - Event details.
	 * 
	 * @return - None.
	 */ 
    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        databaseConn = true;
        int groupID;
        java.sql.Date dob_sqldate = null;
        java.sql.Date meetdate_sqldate = null;
        String firstname = fname.getText();
        String minitial = minit.getText();
        String lastname = lname.getText();
        String sex = gender.getSelectedItem().toString();
        Date dateofbirth = dob.getDate();
        String street = address.getText();
        String city_add = city.getText();
        String state_add = state.getText();
        String pincode = postcode.getText();
        String phn = phone.getText();
        String email = EmailID.getText();
        Date meetdate = meetDate.getDate();
        String temp = groupidcombo.getSelectedItem().toString();
        if (temp == "") {
            groupID = 0;
        } else {
            groupID = Integer.parseInt(temp);
        }
      
        try {
            
            if (dateofbirth != null) {
                dob_sqldate = new java.sql.Date(dateofbirth.getTime());
            }
            if (meetdate != null) {
                meetdate_sqldate = new java.sql.Date(meetdate.getTime());
            }
         
            if (isEmptyFieldData()) {
                System.out.println("Is empty field"+isEmptyFieldData());
                JOptionPane.showMessageDialog(null, "Cannot create an empty record");
                return;
            }
            if (!email.isEmpty()) {
                boolean valid = emailvalidate(email.trim());
                if (!valid) {
                    System.out.println("Email is valid : " + email + " , " + valid);
                    JOptionPane.showMessageDialog(null, "Please enter valid email address", email, JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            if (!phn.isEmpty()) {
                boolean valid = phonevalidate(phn.trim());
                if (!valid) {
                    System.out.println("Phone is valid : " + phn + " , " + valid);
                    JOptionPane.showMessageDialog(null, "Please enter valid phone number", phn, JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            if (!pincode.isEmpty()) {
                boolean valid = zipvalidate(pincode.trim());
                if (!valid) {
                    System.out.println("Zip is valid : " + pincode + " , " + valid);
                    JOptionPane.showMessageDialog(null, "Please enter valid zipcode", pincode, JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            conn = (java.sql.Connection) contactmanager.JDBCconn.sqlconn();
            stmt = conn.prepareCall("{ call Insert_contact(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            stmt.setString(1, firstname);
            stmt.setString(2, minitial);
            stmt.setString(3, lastname);
            stmt.setString(4, sex);
            stmt.setDate(5, dob_sqldate);
            stmt.setString(6, street);
            stmt.setString(7, city_add);
            stmt.setString(8, state_add);
            stmt.setString(9, pincode);
            stmt.setString(10, phn);
            stmt.setString(11, email);
            stmt.setDate(12,meetdate_sqldate);
            stmt.setInt(13, groupID);
            stmt.execute();
            getData();
            cleardata();
        } catch (HeadlessException | SQLException e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }//GEN-LAST:event_addButtonActionPerformed

	/**
	 * This function handles the delete button click. It invokes
	 * the stored procedure to delete from the database based on the selected row id.
	 * addButtonActionPerformed() - Delete button click action handler.
	 * @evt - Event details.
	 * 
	 * @return - None.
	 */
    private void DelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DelButtonActionPerformed
        databaseConn = true;
        try {
            conn = (java.sql.Connection) contactmanager.JDBCconn.sqlconn();
            stmt = conn.prepareCall("{ call DeleteRecords(?)}");
            System.out.println(selectedContactID);
            stmt.setInt(1, selectedContactID);
            stmt.execute();
            System.out.println("Record is deleted from conatct table!");
            getData();
            cleardata();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_DelButtonActionPerformed

  
	/**
	 * This function handles the mouse click on the row. The primary key is
	 * retrieved from the database when the mouse select any rows in the JTable 
	 * addButtonActionPerformed() - Mouse click action handler.
	 * @evt - Event details.
	 * 
	 * @return - None.
	 */ 
    private void JContactTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JContactTableMouseClicked
        System.out.println("contactmanager.ContactManagerSwing.JContactTableMouseClicked()");
        databaseConn = true;
        
        try{
            int Selectedrow_index = JContactTable.getSelectedRow();
            String firstname = getValueAt(Selectedrow_index, 0);
            String middleinit = getValueAt(Selectedrow_index, 1);
            String lastname = getValueAt(Selectedrow_index, 2);
            String sex = getValueAt(Selectedrow_index, 3);
            String dob_date = getValueAt(Selectedrow_index, 4);
            String street = getValueAt(Selectedrow_index, 5);
            String city_add = getValueAt(Selectedrow_index, 6);
            String state_add = getValueAt(Selectedrow_index, 7);
            String pincode = getValueAt(Selectedrow_index, 8);
            String phn = getValueAt(Selectedrow_index, 9);
            String email = getValueAt(Selectedrow_index, 10);
            String meetdate = getValueAt(Selectedrow_index, 11);
            String grpID = getValueAt(Selectedrow_index, 12);
            
        
            fname.setText(firstname);
            minit.setText(middleinit);
            lname.setText(lastname);
            gender.setSelectedItem(sex);
            address.setText(street);
            city.setText(city_add);
            state.setText(state_add);
            postcode.setText(pincode);
            phone.setText(phn);
            EmailID.setText(email);
            groupidcombo.setSelectedItem(grpID);
            if (meetdate != "")
                meetDate.setDate(formater.parse(meetdate));    
            if (dob_date != "")
                dob.setDate(formater.parse(dob_date));
            selectedContactID = Integer.parseInt(hashmap.get(Selectedrow_index).toString());
            
        }catch(NumberFormatException | ParseException e){
            System.out.println("Exeption occured"+ e.getMessage());
        }
    }//GEN-LAST:event_JContactTableMouseClicked

	/**
	 * This function handles the update button click. It retrieves all the data from the UI and invokes
	 * the stored procedure to update the database for the same contact id.
	 * addButtonActionPerformed() - Update button click action handler.
	 * @evt - Event details.
	 * 
	 * @return - None.
	 */ 
    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        databaseConn = true;
        java.sql.Date dob_sqldate = null;
        java.sql.Date meet_sqldate = null;
        int groupID;
        String firstname = fname.getText();
        String minitial = minit.getText();
        String lastname = lname.getText();
        String sex = gender.getSelectedItem().toString();
        String street = address.getText();
        String city_add = city.getText();
        String state_add = state.getText();
        String pincode = postcode.getText();
        String phn = phone.getText();
        String email = EmailID.getText();
        Date meetdate = meetDate.getDate();
        Date birthdate = dob.getDate();
        String temp = groupidcombo.getSelectedItem().toString();
       
        try {
            if (isEmptyFieldData()) {
                JOptionPane.showMessageDialog(null, "Cannot update an empty record");
                return;
            }
            if (temp == "") {
                groupID = 0;
            } else {
                groupID = Integer.parseInt(temp);
            }
            if (birthdate != null) {
                dob_sqldate = new java.sql.Date(birthdate.getTime());
            }
            if (meetdate != null) {
               meet_sqldate = new java.sql.Date(meetdate.getTime()); 
            }
            
            if (!email.isEmpty()) {
                boolean valid = emailvalidate(email.trim());
                if (!valid) {
                    System.out.println("Email is valid : " + email + " , " + valid);
                    JOptionPane.showMessageDialog(null, "Please enter valid email address", email, JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            if (!phn.isEmpty()) {
                boolean valid = phonevalidate(phn.trim());
                if (!valid) {
                    System.out.println("Phone is valid : " + phn + " , " + valid);
                    JOptionPane.showMessageDialog(null, "Please enter valid phone number", phn, JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            if (!pincode.isEmpty()) {
                boolean valid = zipvalidate(pincode.trim());
                if (!valid) {
                    System.out.println("Zip is valid : " + pincode + " , " + valid);
                    JOptionPane.showMessageDialog(null, "Please enter valid zipcode", pincode, JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            conn = (java.sql.Connection) contactmanager.JDBCconn.sqlconn();
            stmt = conn.prepareCall("{ call updateRecords(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            System.out.println("selected id" + selectedContactID);
            stmt.setInt(1, selectedContactID);
            stmt.setString(2, firstname);
            stmt.setString(3, minitial);
            stmt.setString(4, lastname);
            stmt.setString(5, sex);
            stmt.setDate(6, dob_sqldate);
            stmt.setString(7, street);
            stmt.setString(8, city_add);
            stmt.setString(9, state_add);
            stmt.setString(10, pincode);
            stmt.setString(11, phn);
            stmt.setString(12, email);
            stmt.setDate(13,meet_sqldate);
            stmt.setInt(14, groupID);
            
            stmt.execute();
            System.out.println("Record is updated in conatct table!");
            getData();
            cleardata();
        } catch (HeadlessException | SQLException e ) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_updateButtonActionPerformed

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
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
            java.util.logging.Logger.getLogger(ContactManagerSwing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ContactManagerSwing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ContactManagerSwing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ContactManagerSwing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ContactManagerSwing().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Communication;
    private javax.swing.JButton DelButton;
    private javax.swing.JTextField EmailID;
    private javax.swing.JTable JContactTable;
    private javax.swing.JButton add;
    private javax.swing.JButton addButton;
    private javax.swing.JTextField address;
    private javax.swing.JTextField city;
    private javax.swing.JButton delete;
    private org.jdesktop.swingx.JXDatePicker dob;
    private javax.swing.JTextField fname;
    private javax.swing.JComboBox<String> gender;
    private javax.swing.JLabel groupid;
    private javax.swing.JComboBox<String> groupidcombo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField lname;
    private org.jdesktop.swingx.JXDatePicker meetDate;
    private javax.swing.JTextField minit;
    private javax.swing.JTextField phone;
    private javax.swing.JTextField postcode;
    private javax.swing.JTextField state;
    private javax.swing.JButton update;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables

}
