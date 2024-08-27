import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;
import javax.swing.JOptionPane;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomTree;
import weka.core.Instance;
import weka.core.Instances;

public class CreatingAlgorithm {
    ReadFile readFile;
    Instances trainingData;
    Instances testData;
    RandomTree randomTree;
    Evaluation eval;
    double kappa;
    double accuarcy;
    double rmse;




    public CreatingAlgorithm(ReadFile readFile) {
        this.readFile = readFile;

    }

    public void modelCreation() throws Exception {

            BufferedReader bufferedReader = new BufferedReader(new FileReader(readFile.file));
            Instances data = new Instances(bufferedReader);
            bufferedReader.close();
            data.setClassIndex(data.numAttributes() - 1);
            J48 j48Classifier = new J48();
            Evaluation evaluation = new Evaluation(data);
            evaluation.crossValidateModel(j48Classifier, data, 10, new Random(1L));
            System.out.println(evaluation.toSummaryString("\nResults", false));


        System.out.print("Successfully executed.");
    }

    public void randomTree() throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(readFile.file));
        Instances dataSet = new Instances(bufferedReader);
        int trainSize = (int)Math.round((double)dataSet.numInstances() * 0.7);
        int testSize = dataSet.numInstances() - trainSize;
        trainingData = new Instances(dataSet, 0, trainSize);
        trainingData.setClassIndex(trainingData.numAttributes() - 1);
        testData = new Instances(dataSet, trainSize, testSize);
        testData.setClassIndex(testData.numAttributes() - 1);
        randomTree = new RandomTree();
        randomTree.buildClassifier(trainingData);

        for(int i = 0; i < testData.numInstances(); ++i) {
            double actualClass = testData.instance(i).classValue();
            String actual = testData.classAttribute().value((int)actualClass);
            Instance newInts = testData.instance(i);
            double predRt = randomTree.classifyInstance(newInts);
            String predString = testData.classAttribute().value((int)predRt);
            System.out.println(actual + " " + predString);
        }
        eval = new Evaluation(trainingData);
        eval.evaluateModel(randomTree, testData);
        kappa = eval.kappa();
        accuarcy=eval.pctCorrect();
        rmse=eval.rootMeanSquaredError();


        JOptionPane.showMessageDialog(null, eval.toSummaryString("\n Değerlendirme Sonuçları: \n", false));
        JOptionPane.showMessageDialog(null, eval.toMatrixString("=== Genel Karışıklık Matrisi ===\n"));
    }


    public void comment() throws Exception {
        String mesaj="";
        JOptionPane pane=new JOptionPane();
        if(kappa>=0.01 && kappa<=0.40  ){
            mesaj+="Kappa: " + kappa +"\n" + "Zayıf Anlaşma";
        }
        else if(kappa>=0.41 && kappa<=0.60   ){
            mesaj+="Kappa: " + kappa +"\n" + "Orta Anlaşma";
        }
        else if(kappa>=0.61 && kappa<=0.80 ){
            mesaj+="Kappa: " + kappa +"\n" + "İyi Anlaşma";
        }
        else if(kappa>=0.81 && kappa<=1 ){
            mesaj+="Kappa: " + kappa +"\n" + "Çok iyi Anlaşma";
        }
        else{
            mesaj+="Kappa hesaplanamadı";
        }

        if(accuarcy>=70){
            mesaj+="Correctly Classified Instances: " + accuarcy +"\n" + "Kabul edilebilir.";

        }
        else {
            mesaj+="Correctly Classified Instances: " + accuarcy +"\n" + "Kabul edilemez.";

        }

        if(rmse<=100){
            mesaj+="Root Relative Squared Error: " + rmse +"\n" + "İyi bir modele yakın.";

        }
        else{
            mesaj+="Root Relative Squared Error: " + rmse +"\n" + "Kötü model.";
        }
        pane.showMessageDialog(null, mesaj);
    }
}
