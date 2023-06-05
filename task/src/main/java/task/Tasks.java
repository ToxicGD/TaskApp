/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package task;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Daimer
 */
public class Tasks extends javax.swing.JFrame {
    private int actividadesCompletadasHoy = 0;
    ArrayList<String> actividadesCompletadas = new ArrayList<>();
    ArrayList<String> tareas = new ArrayList<String>();
    DefaultTableModel tlModel;
    private javax.swing.JLabel lblMensaje;
    private javax.swing.JComboBox<String> cmbCategorias;
    
    DefaultTableModel tlModelHoy;
    DefaultTableModel tlModelSemana;
    DefaultTableModel tlModelMes;
    
    public Tasks() {
        initComponents();
        
        createTask();
        
        lblMensaje = new javax.swing.JLabel();
        jPanel3.add(lblMensaje);
        
        // Agrega un JComboBox para las categorías
        cmbCategorias = new javax.swing.JComboBox<>();
        jPanel2.add(cmbCategorias);
        
        // Agrega los DefaultTableModel para cada categoría
        String[] titulosHoy = {"Tareas", "Acciones"};
        tlModelHoy = new DefaultTableModel(null, titulosHoy);
        
        String[] titulosSemana = {"Tareas", "Acciones"};
        tlModelSemana = new DefaultTableModel(null, titulosSemana);
        
        String[] titulosMes = {"Tareas", "Acciones"};
        tlModelMes = new DefaultTableModel(null, titulosMes);
    }

    public void createTask(){
        String[] titulos = {"Tareas", "Acciones"};
        tlModel = new DefaultTableModel(null, titulos);
        tlModel.setRowCount(0);

        for (int i = 0; i < tareas.size(); i++) {
            final int index = i;
            JButton btnCheck = new JButton("Completar");
            btnCheck.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    buttonChecked(index);
                }
            });

            if (actividadesCompletadas.contains(tareas.get(i))) {
                btnCheck.setText("Completado");
                btnCheck.setEnabled(false); // Deshabilitar el botón para las tareas completadas
            }

            Object[] rowData = { tareas.get(i), btnCheck };
            tlModel.addRow(rowData);
        }

        tlTareas.setModel(tlModel);
        tlTareas.getColumn("Acciones").setCellRenderer(new ButtonRenderer());
        tlTareas.getColumn("Acciones").setCellEditor(new ButtonEditor(new JCheckBox()));
    }
    
    
   public void buttonChecked(int recive) {
    JButton btnCheck = (JButton) tlTareas.getModel().getValueAt(recive, 1);
    String buttonText = btnCheck.getText(); // Obtener el texto del botón
    btnCheck.setEnabled(false);
    actividadesCompletadas.add(tareas.get(recive));
    actividadesCompletadasHoy++;
    
    // Actualizar el mensaje y bloquear la tarea
    lblMensaje.setText("La tarea '" + tareas.get(recive) + "' se ha completado.");
    tareas.remove(recive);
    createTask();
}
    
    // Clase renderizadora de botones en la tabla
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(UIManager.getColor("Button.background"));
            }

            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // Clase editora de botones en la tabla
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;

        private String label;
        private boolean isPushed;
        private int row;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            fireEditingStopped();
        }
    });
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            
            this.row = row;
            
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(table.getBackground());
            }

            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                // Realizar alguna acción cuando se hace clic en el botón
                int rowIndex = tlTareas.convertRowIndexToModel(row); // Obtener el índice de la fila en el modelo
                String task = tlModel.getValueAt(rowIndex, 0).toString(); // Obtener el texto de la columna de tareas
                System.out.println(task);
            }
            isPushed = false;
            return button.getText(); // Devolver el texto del botón
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
    
    public void filterTasksByCategory(String categoria) {
        // Limpia los modelos de tabla de cada categoría
        tlModelHoy.setRowCount(0);
        tlModelSemana.setRowCount(0);
        tlModelMes.setRowCount(0);
        
        for (int i = 0; i < tareas.size(); i++) {
            String tarea = tareas.get(i);
            
            // Filtra las tareas según la categoría seleccionada
            // y las agrega al modelo de tabla correspondiente
            if (categoria.equals("Hoy")) {
                if (actividadesCompletadas.contains(tarea)) {
                    continue; // Ignora las tareas ya completadas
                }
                Object[] rowData = { tarea, "Completar" };
                tlModelHoy.addRow(rowData);
            } else if (categoria.equals("Semana")) {
                // Lógica para filtrar tareas para esta semana
                // y agregarlas al modelo de tabla tlModelSemana
            } else if (categoria.equals("Mes")) {
                // Lógica para filtrar tareas para este mes
                // y agregarlas al modelo de tabla tlModelMes
            }
        }
        
        // Muestra las tareas filtradas en las secciones correspondientes
        if (categoria.equals("Hoy")) {
            tlTareas.setModel(tlModelHoy);
        } else if (categoria.equals("Semana")) {
            tlTareas.setModel(tlModelSemana);
        } else if (categoria.equals("Mes")) {
            tlTareas.setModel(tlModelMes);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tlTareas = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 146, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 77, Short.MAX_VALUE)
                .addComponent(jButton1))
        );

        tlTareas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tlTareas);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         String tareax = JOptionPane.showInputDialog("Nueva tarea");
        tareas.add(tareax);

        createTask();
        
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tlTareas;
    // End of variables declaration//GEN-END:variables
}
