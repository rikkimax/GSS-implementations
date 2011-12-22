package gss.ircbot

import java.util.zip.ZipInputStream
import java.util.zip.ZipFile
import java.util.zip.ZipEntry
import gss.run.BootAll
/**
 * Runs the IRC bot.
 */

class Runner {
    static void main(String[] args) {
        //extract all game config files
        BooterHelp.getConfig();
        //start the servers!
        BootAll.main("login");
    }
}

class BooterHelp {

    static void getConfig() {
        if (!new File("game").exists())
            extractMyJARs("");
        if (new File("binlib").exists())
            new File("binlib").renameTo(new File("game"));
    }

    private static void extractMyJARs(String dest) {
        try {
            String home = Runner.class.getResource("Runner.class").getFile();
            home = home.substring(6, home.indexOf("!"));
            if (home == null) {
                copyFolder(new File("src/main/resources/game"), new File("game"));
            } else {
                byte[] buf = new byte[1024];
                ZipInputStream zipinputstream = new ZipInputStream(new FileInputStream(home));
                ZipFile zf = new ZipFile(home);
                Enumeration entries = zf.entries();
                while (entries.hasMoreElements()) {
                    ZipEntry entry = (ZipEntry) entries.nextElement();
                    if (entry.getName().contains("main")) {
                        copyInputStream(zf.getInputStream(entry),
                                new BufferedOutputStream(new FileOutputStream(entry.getName().substring(5))));
                        home = entry.getName().substring(5);
                    }
                }
                zipinputstream = new ZipInputStream(new FileInputStream(home));
                zf = new ZipFile(home);
                entries = zf.entries();
                while (entries.hasMoreElements()) {
                    ZipEntry entry = (ZipEntry) entries.nextElement();
                    if (entry.getName().contains("game")) {
                        if (entry.getName().contains("/"))
                            new File(entry.getName().substring(0, entry.getName().lastIndexOf("/"))).mkdirs();
                        if (entry.isDirectory())
                            (new File(entry.getName())).mkdir();
                        else
                            copyInputStream(zf.getInputStream(entry),
                                    new BufferedOutputStream(new FileOutputStream(entry.getName())));
                    }
                }
                zf.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getJarFileName() {
        String myClassName = Runner.getClass().getName() + ".class";
        URL urlJar = Runner.getClass().getClassLoader().getSystemResource(myClassName);
        String urlStr = urlJar.toString();
        int from = "jar:file:".length();
        int to = urlStr.indexOf("!/");
        return urlStr.substring(from, to);
    }

    private static void copyFolder(File src, File dest) throws IOException {
        if (src.isDirectory()) {
            if (!dest.exists())
                dest.mkdir();
            String[] files = src.list();
            for (String file: files) {
                File srcFile = new File(src, file);
                File destFile = new File(dest, file);
                copyFolder(srcFile, destFile);
            }
        } else {
            InputStream inI = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inI.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            inI.close();
            out.close();
        }
    }

    private static final void copyInputStream(InputStream inI, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inI.read(buffer)) >= 0)
            out.write(buffer, 0, len);
        inI.close();
        out.close();
    }
}