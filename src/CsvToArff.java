import java.io.File;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

    public class CsvToArff {
        ReadFile readFile;

        public CsvToArff(ReadFile readFile){
            this.readFile=readFile;
        }
        public void file() throws Exception {
            File csvfile=readFile.file;
            String turning=csvfile.getAbsolutePath().replace(".csv",".arff");
            CSVLoader loader = new CSVLoader();
            loader.setSource(csvfile);
            Instances dataSet = loader.getDataSet();

            ArffSaver saver = new ArffSaver();
            saver.setInstances(dataSet);
            File arffFile=new File(turning);
            saver.setFile(arffFile);
            saver.writeBatch();

            readFile.file=arffFile;
            System.out.println("Arff dosya adı: " + readFile.file.getName());


        }
    }



