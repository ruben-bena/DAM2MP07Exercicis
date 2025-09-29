package com.project;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ControllerDesktop {

    @FXML
    private Text TEXTO_PRUEBA;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    public VBox yPane;
    private JSONArray jsonInfoCharacters, jsonInfoConsoles, jsonInfoGames;
    private URL resource = this.getClass().getResource("/assets/listItemDesktop.fxml");

    public void initialize() {
        try {
            // Añade los valores del ComboBox
            comboBox.getItems().addAll("Characters", "Consoles", "Games");
            comboBox.getSelectionModel().selectFirst();

            // Listener del ComboBox
            comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                evaluateComboBoxOption(newValue);
            });

            // Obtener lista Characters
            URL jsonCharactersFileURL = getClass().getResource("/assets/characters.json");
            Path path = Paths.get(jsonCharactersFileURL.toURI());
            String content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
            jsonInfoCharacters = new JSONArray(content);

            // Obtener lista Consoles
            URL jsonConsolesFileURL = getClass().getResource("/assets/consoles.json");
            path = Paths.get(jsonConsolesFileURL.toURI());
            content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
            jsonInfoConsoles = new JSONArray(content);

            // Obtener lista Games
            URL jsonGamesFileURL = getClass().getResource("/assets/games.json");
            path = Paths.get(jsonGamesFileURL.toURI());
            content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
            jsonInfoGames = new JSONArray(content);

            // Actualiza la UI con los valores iniciales de los personajes
            setCharacters(null);

            // Añade

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    @FXML
    private void setCharacters(ActionEvent event) throws Exception {
        // Borrar contenido listView
        yPane.getChildren().clear();

        // Generar la nueva lista a partir de 'jsonInfoCharacters'
        for (int i = 0; i < jsonInfoCharacters.length(); i++) {
            // Obtenir el objeto JSON individual (character)
            JSONObject character = jsonInfoCharacters.getJSONObject(i);

            // Extraer información necesaria del JSON
            String name = character.getString("name");

            // Carregar el template de 'listItem.fxml'
            FXMLLoader loader = new FXMLLoader(resource);
            Parent itemTemplate = loader.load();
            ControllerListItemDesktop itemController = loader.getController();

            // Asignar los valores a los controles del template
            itemController.setLableName(name);
            if (name.equals("Samus Aran")) {
                name = "samus";
            } else if (name.equals("Donkey Kong")) {
                name = "dk";
            }
            itemController.setImatge("/assets/images/character_" + name.toLowerCase() + ".png");

            // Añadir el nuevo elemento a 'yPane'
            yPane.getChildren().add(itemTemplate);
        }
    }

    @FXML
    private void setConsoles(ActionEvent event) throws Exception {
        // Borrar contenido listView
        yPane.getChildren().clear();

        // Generar la nueva lista a partir de 'jsonInfoCharacters'
        for (int i = 0; i < jsonInfoConsoles.length(); i++) {
            // Obtenir el objeto JSON individual (character)
            JSONObject console = jsonInfoConsoles.getJSONObject(i);

            // Extraer información necesaria del JSON
            String name = console.getString("name");

            // Carregar el template de 'listItem.fxml'
            FXMLLoader loader = new FXMLLoader(resource);
            Parent itemTemplate = loader.load();
            ControllerListItemDesktop itemController = loader.getController();

            // Asignar los valores a los controles del template
            itemController.setLableName(name);
            if (name.equals("Nintendo Switch")) {
                name = "switch";
            } else if (name.equals("Wii U")) {
                name = "wiiu";
            } else if (name.equals("Nintendo 64")) {
                name = "64";
            } else if (name.equals("Super Nintendo")) {
                name = "sn";
            }
            itemController.setImatge("/assets/images/nintendo_" + name.toLowerCase() + ".png");

            // Añadir el nuevo elemento a 'yPane'
            yPane.getChildren().add(itemTemplate);
        }
    }

    @FXML
    private void setGames(ActionEvent event) throws Exception {
        // Borrar contenido listView
        yPane.getChildren().clear();

        // Generar la nueva lista a partir de 'jsonInfoCharacters'
        for (int i = 0; i < jsonInfoGames.length(); i++) {
            // Obtenir el objeto JSON individual (character)
            JSONObject character = jsonInfoGames.getJSONObject(i);

            // Extraer información necesaria del JSON
            String name = character.getString("name");

            // Carregar el template de 'listItem.fxml'
            FXMLLoader loader = new FXMLLoader(resource);
            Parent itemTemplate = loader.load();
            ControllerListItemDesktop itemController = loader.getController();

            // Asignar los valores a los controles del template
            itemController.setLableName(name);
            if (name.equals("The Legend of Zelda")) {
                name = "zelda";
            } else if (name.equals("Pokémon Red i Blue")) {
                name = "pred";
            } else if (name.equals("Mario Kart 64")) {
                name = "smk";
            } else if (name.equals("Donkey Kong")) {
                name = "dk";
            } else if (name.equals("Super Mario Bros")) {
                name = "smb";
            }

            String extension = ".png";
            if (name.equals("Metroid") || name.equals("pred") || name.equals("smk")) {
                extension = ".jpeg";
            }

            itemController.setImatge("/assets/images/game_" + name.toLowerCase() + extension);

            // Añadir el nuevo elemento a 'yPane'
            yPane.getChildren().add(itemTemplate);
        }
    }

    @FXML
    private void evaluateComboBoxOption(String option) {
        try {
            switch (option) {
                case "Characters":
                    setCharacters(null);
                    break;
                case "Consoles":
                    setConsoles(null);
                    break;
                case "Games":
                    setGames(null);
                    break;
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}