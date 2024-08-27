import java.io.File;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

    public class CsvToArff {
        public void file() throws Exception {
            CSVLoader loader = new CSVLoader();
            loader.setSource(new File("C:\\Users\\kelif\\Desktop\\musteri_tahmin.csv"));
            Instances data = loader.getDataSet();
            data.setClassIndex(data.numAttributes() - 1);
            ArffSaver saver = new ArffSaver();
            saver.setInstances(data);
            saver.setFile(new File("C:\\Users\\kelif\\Desktop\\musteri_tahmin.arff"));
            saver.writeBatch();
        }
    }


