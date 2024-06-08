package de.x8games.dobby;





import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;



public class Settings {

    private static Settings	instance	= null;
    private static String	path		= null;
    private static String	filename	= null;

    synchronized public static Settings I() {
        if (instance == null) {
            File file = new File(path);
            if (!file.exists()) {
                if (!file.mkdirs()) {
                    LogManager.getLogger().error("konnte Verzeichnis nicht erzeugen");
                    LogManager.getLogger().error("DIR: " + path);
                } else {
                    LogManager.getLogger().debug("Verzeichnis wurde erstellt");
                }
            } else {
                LogManager.getLogger().debug("Verzeichnis bereits vorhanden");
            }
            instance = new Settings(path, filename);
        }
        return instance;
    }

    synchronized public static void init(String path, String filename) {
        Settings.path = path;
        Settings.filename = filename;
        LogManager.getLogger().debug("Settings-Path: " + path);
    }
    /**
     * die Einstellungen für einen Server/Befehle/$WhatEver
     *
     * @param file
     */
    public Settings(String directory, String file) {
        this.file = file;
        this.directory = directory;

        this.props = new SortedProperties();
        if (file != null) {
            try {
                props.load(new FileInputStream(this.directory + "/" + file));
                LogManager.getLogger().debug("Properties geladen von '" + this.directory + "/" + file + "'");
            } catch(FileNotFoundException e) {
                LogManager.getLogger().error(e);
            } catch(IOException e) {
                LogManager.getLogger().error(e);
            }
        } else {
            LogManager.getLogger().debug("verwende ein temporäres Einstellungfile");
        }
    }



    /**
     * sortiert die Properties nach Alphabet - dadurch sind sie in der Datei leichter zu lesen
     */
    @SuppressWarnings({ "serial", "unchecked", "rawtypes" })
    public static class SortedProperties extends Properties {
        public Enumeration keys() {
            Enumeration keysEnum = super.keys();
            Vector<String> keyList = new Vector<String>();
            while(keysEnum.hasMoreElements())
                keyList.add((String) keysEnum.nextElement());
            Collections.sort(keyList);
            return keyList.elements();
        }
    }



    private String		file;
    private String		directory;
    private Properties	props;



    public void Save(String file) {
        this.file = file;
        Save();
    }

    public boolean Save() {
        if (file == null) return false;
        try {
            props.store(new FileOutputStream(directory + "/" + file), "Workspace");
            return true;
        } catch(FileNotFoundException e) {
            LogManager.getLogger().error(e);
        } catch(IOException e) {
            LogManager.getLogger().error(e);
        }
        return false;
    }

    public void set(String key, String value) {
        props.setProperty(key, value);
    }

    public void set(String key, int value) {
        props.setProperty(key, Integer.toString(value));
    }

    public void set(String key, double value) {
        props.setProperty(key, Double.toString(value));
    }

    public void set(String key, boolean value) {
        props.setProperty(key, Boolean.toString(value));
    }

    public String get(String key, String __default) {
        Object value = props.get(key);
        if (value == null) {
            value = __default;
            props.setProperty(key, __default);
            try {
                Save();
            } catch(Exception e) {
                LogManager.getLogger().error(e);
            }
        }
        return (String) value;
    }

    public int get(String key, int __default) {
        return Integer.parseInt((String) get(key, Integer.toString(__default)));
    }

    public double get(String key, double __default) {
        return Double.parseDouble((String) get(key, Double.toString(__default)));
    }

    public boolean get(String key, boolean __default) {
        return Boolean.parseBoolean((String) get(key, Boolean.toString(__default)));
    }

    public String getString(String key) {
        return (String) get(key, "n/a");
    }

    public int getInteger(String key) {
        return Integer.parseInt((String) get(key, "0"));
    }

    public double getDouble(String key) {
        return Double.parseDouble((String) get(key, "0.0"));
    }

}
