package org.example;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class REPRODUCTOR extends JFrame {
    private DefaultListModel<Nodo> playlistModel;
    private JList<Nodo> playlistList;
    private JTextArea textArea;




    public REPRODUCTOR() {
        setTitle("Reproductor de Musica");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//EXIT_ON_CLOSE
        setLocationRelativeTo(null);

        // Crear el modelo de la lista de reproducción
        playlistModel = new DefaultListModel<>();
        playlistList = new JList<>(playlistModel);
        JScrollPane scrollPane = new JScrollPane(playlistList);

        // Área de texto para mostrar información detallada de la canción seleccionada
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane textScrollPane = new JScrollPane(textArea);

        // Aca creamos los botones
        JButton addButton = new JButton("Agregar");
        JButton removeButton = new JButton("Eliminar");
        JButton sortButton = new JButton("Ordenar");
        JButton searchButton = new JButton("Buscar");
        JButton exportButton = new JButton("Exportar");
        JButton importButton = new JButton("Importar");

        // Creamos un metodo de busqueda
        JTextField searchField = new JTextField();

        // Diseñamos el control de y agregamos los botones;  Layout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 6)); // Se añade un botón más
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(sortButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(exportButton);
        buttonPanel.add(importButton); // Se añade el nuevo botón....aqui ando

        //Creamos un subpanel searchPanel
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(new JLabel("Buscar: "), BorderLayout.WEST);// West
        searchPanel.add(searchField, BorderLayout.CENTER);//CENTER

        //Creamos otro subpanel y le agregamos
        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.add(buttonPanel, BorderLayout.NORTH);
        controlPanel.add(scrollPane, BorderLayout.CENTER);
        controlPanel.add(searchPanel, BorderLayout.SOUTH);


        JPanel leftPanel = new JPanel(new BorderLayout());

        leftPanel.add(new JLabel("Propiedades"), BorderLayout.NORTH);//era nort
        leftPanel.add(textScrollPane, BorderLayout.CENTER);//CENTER
        leftPanel.add(controlPanel, BorderLayout.SOUTH);

        setLayout(new GridLayout(1, 2));

        //agregamos el Panel con los sus atributos, subpaneles y otros dentro de ellos
        add(leftPanel);



        // Programamos el boton Agregar
        addButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Ingrese el nombre de la canción:");
            String artist = JOptionPane.showInputDialog("Ingrese el nombre del artista:");
            String genre = JOptionPane.showInputDialog("Ingrese el género:");
            int duration = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la duración (segundos):"));

            Nodo song = new Nodo(name, artist, genre, duration);
            playlistModel.addElement(song);
        });

        // Programamos el boton Eliminar
        removeButton.addActionListener(e -> {
            int selectedIndex = playlistList.getSelectedIndex();
            if (selectedIndex != -1) {
                playlistModel.remove(selectedIndex);
            }
        });

        // Programamos el boton Ordenar
        //sortButton.addActionListener(e -> ORDENAR());
        sortButton.addActionListener(e -> ORDENAR());

        //Programamos el boton buscar
        searchButton.addActionListener(e -> {
            String searchTerm = searchField.getText();
            BUSQUEDA(searchTerm);
        });

        // Programamos el boton exportar con el metodo EXPORTACION
        exportButton.addActionListener(e -> EXPORTACION());


        // Programamos el boton de importar con el boton IMPORTACION
        //importButton.addActionListener(e -> L.IMPORTACION());
        importButton.addActionListener(e -> IMPORTACION());


        // Listener "es para mostrar los datos de la cancion al tocarlo"
        playlistList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Nodo selectedSong = playlistList.getSelectedValue();
                if (selectedSong != null) {
                    String info = "Nombre: " + selectedSong.getCancion() + "\n" +
                            "Artista: " + selectedSong.getArtista() + "\n" +
                            // "Artista: " + selectedSong.getNombreArtistaEnEspanol() + "\n" +
                            "Género: " + selectedSong.getGenero() + "\n" +
                            //"Género: " + selectedSong.getNombreGeneroEnEspanol() + "\n" +
                            "Duración: " + selectedSong.getDuracion() + " segundos";
                    textArea.setText(info);
                }
            }
        });
    }

    private void IMPORTACION() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Importar Playlist");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de texto", "txt"));
        int userSelection = fileChooser.showOpenDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToOpen = fileChooser.getSelectedFile();
            try {
                FileReader reader = new FileReader(fileToOpen);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line;


                // Limpiar la lista actual antes de cargar la nueva playlist
                playlistModel.clear();
                while ((line = bufferedReader.readLine()) != null) {
                    String nombre = line.substring(8); // "Nombre: " tiene 8 caracteres
                    String artista = bufferedReader.readLine().substring(9); // "Artista: " tiene 9 caracteres
                    String genero = bufferedReader.readLine().substring(8); // "Género: " tiene 8 caracteres
                    int duracion = Integer.parseInt(bufferedReader.readLine().substring(11)); // "Duración: " tiene 11 caracteres
                    Nodo song = new Nodo(nombre, artista, genero, duracion);
                    playlistModel.addElement(song);
                    bufferedReader.readLine(); // Leer línea en blanco
                }
                reader.close();
                JOptionPane.showMessageDialog(this, "Playlist importada exitosamente.");
            } catch (IOException | NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Error al importar la playlist: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void ORDENAR() {
        List<Nodo> songs = new LinkedList<>();
        for (int i = 0; i < playlistModel.size(); i++) {
            songs.add(playlistModel.getElementAt(i));
        }

        // Ordenar la lista de reproducción por nombre de canción
        songs.sort(Comparator.comparing(Nodo::getCancion));

        // Actualizar el modelo de la lista de reproducción
        playlistModel.removeAllElements();
        for (Nodo song : songs) {
            playlistModel.addElement(song);
        }
    }

    private void BUSQUEDA(String searchTerm) {
        playlistList.clearSelection();
        for (int i = 0; i < playlistModel.size(); i++) {
            Nodo song = playlistModel.getElementAt(i);
            if (song.getCancion().equalsIgnoreCase(searchTerm))
            song.getArtista().equalsIgnoreCase(searchTerm);
            //song.getNombreArtistaEnEspanol().equalsIgnoreCase(searchTerm);
            //song.getNombreGeneroEnEspanol().equalsIgnoreCase(searchTerm);
            song.getGenero().equalsIgnoreCase(searchTerm); {
                playlistList.setSelectedIndex(i);
                playlistList.ensureIndexIsVisible(i);
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Canción no encontrada: " + searchTerm);
    }


    private void EXPORTACION() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Exportar Playlist");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de texto", "txt"));
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try {
                FileWriter writer = new FileWriter(fileToSave);
                for (int i = 0; i < playlistModel.size(); i++) {
                    Nodo song = playlistModel.getElementAt(i);
                    writer.write("Nombre: " + song.getCancion() + "\n");
                    writer.write("Artista: " + song.getArtista() + "\n");
                    //writer.write("Artista: " + song.getNombreArtistaEnEspanol() + "\n");
                    writer.write("Género: " + song.getGenero() + "\n");
                    //writer.write("Género: " + song.getNombreGeneroEnEspanol() + "\n");
                    writer.write("Duración: " + song.getDuracion() + "\n\n"); // Solo el número de segundos
                }
                writer.close();
                JOptionPane.showMessageDialog(this, "Playlist exportada exitosamente.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al exportar la playlist: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }









    }