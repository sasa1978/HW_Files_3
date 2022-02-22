import java.io.*;
import java.util.zip.*;

public class Main {
    public static void main(String[] args) {
        String workDir = "C:\\Games\\savegames\\";

        if (openZip(workDir + "zip.zip", workDir)) {
            File dir = new File(workDir);
            if (dir.listFiles() != null) {
                for (File item : dir.listFiles()) {
                    if (!item.getName().equals("zip.zip")) {
                        System.out.println(openProgress(item.getPath()));
                    }
                }
            }
        }
    }

    public static boolean openZip(String file, String dir) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(file))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fout = new FileOutputStream(dir + name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    public static GameProgress openProgress(String file) {
        GameProgress gameProgress = null;
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return gameProgress;
    }
}
