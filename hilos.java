package hilos;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class hilos {

    public static void main(String[] args) {
        Thread musicaThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File cancion = new File("quema.wav");
                    AudioInputStream archivo = AudioSystem.getAudioInputStream(cancion);
                    Clip clip = AudioSystem.getClip();
                    clip.open(archivo);
                    clip.start();
                    Thread.sleep(clip.getMicrosecondLength() / 1000);
                } catch (Exception e) {
                    System.out.println("Error al reproducir la música: " + e.getMessage());
                }
            }
        });

        Thread entradaDatosThread = new Thread(() -> {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Ingresa tu nombre: ");
                String nombre = scanner.nextLine();
                System.out.println("Hola, " + nombre + "! ¿Cómo estás?");
            } catch (Exception e) {
                System.out.println("Error al obtener datos: " + e.getMessage());
            }
        });

        musicaThread.start();
        entradaDatosThread.start();

        try {
            musicaThread.join();
            entradaDatosThread.join();
        } catch (InterruptedException e) {
            System.out.println("Error al esperar a que los hilos terminen: " + e.getMessage());
        }
    }
}
