package core;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileMnemonicMapLoader implements MnemonicMapLoader {

    private static final String DELIMETER = "\\|";
    private final BufferedReader[] readers;

    public FileMnemonicMapLoader(File... dataFile) throws FileNotFoundException {
        this(getBufferedReaders(dataFile));
    }

    private static BufferedReader[] getBufferedReaders(File[] dataFiles) throws FileNotFoundException {
        List<BufferedReader> readers = new ArrayList();
        for (File dataFile : dataFiles) {
            readers.add(new BufferedReader(new FileReader(dataFile)));
        }
        return readers.toArray(new BufferedReader[readers.size()]);
    }

    public FileMnemonicMapLoader(BufferedReader... readers) {
        this.readers = readers;
    }

    @Override
    public void load(MnemonicMap map) throws IOException {
        for (BufferedReader reader : readers) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] split = line.trim().split(DELIMETER);
                if (split.length == 3) {
                    map.add(new MatchingMnemonic(split[2], new MatchingMnemonicDetail(split[1], split[0])));
                }
            }
//            reader.close();
        }
    }
}
