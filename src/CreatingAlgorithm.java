import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.RandomTree;
import weka.core.Instance;
import weka.core.Instances;



public class CreatingAlgorithm {
    ReadFile readFile;
    Instances trainingData;
    Instances testData;
    double kappa;
    double accuracy;
    double rmse;
    Instances dataSet;

    public CreatingAlgorithm(ReadFile readFile) {
        this.readFile = readFile;

    }

    public void randomTree() throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(readFile.file));
        dataSet = new Instances(bufferedReader);
        int trainSize = (int) Math.round((double) dataSet.numInstances() * 0.7);
        int testSize = dataSet.numInstances() - trainSize;
        trainingData = new Instances(dataSet, 0, trainSize);
        trainingData.setClassIndex(trainingData.numAttributes() - 1);
        testData = new Instances(dataSet, trainSize, testSize);
        testData.setClassIndex(testData.numAttributes() - 1);

        RandomTree randomTree = new RandomTree();
        randomTree.buildClassifier(trainingData);
        for (int i = 0; i < testData.numInstances(); ++i) {
            double actualClass = testData.instance(i).classValue();
            String actual = testData.classAttribute().value((int) actualClass);
            Instance newInts = testData.instance(i);
            double predRt = randomTree.classifyInstance(newInts);
            String predString = testData.classAttribute().value((int) predRt);
            System.out.println(actual + " " + predString);
        }
        Evaluation eval = new Evaluation(trainingData);
        eval.evaluateModel(randomTree, testData);
        try {
            if (eval.confusionMatrix() != null && eval.confusionMatrix().length > 0) {
                kappa = eval.kappa();
                accuracy = eval.pctCorrect();
                rmse = eval.rootRelativeSquaredError();
                JOptionPane.showMessageDialog(null, eval.toSummaryString("\n Değerlendirme Sonuçları: \n", false));
                JOptionPane.showMessageDialog(null, eval.toMatrixString("=== Genel Karışıklık Matrisi ===\n"));
            } else {
                JOptionPane.showMessageDialog(null, "Hesaplama yapılamadı.Dosyanızı kontorl ediniz.");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Dosya formatınız yanlış olabilir,hesap yapılamıyor!");

        }
    }




    public void comment() throws Exception {
        DecimalFormat df = new DecimalFormat("#.####");
        String message = kappaComment(kappa,df)+accuracyComment(accuracy,df)+rmseComment(rmse,df);
        JOptionPane.showMessageDialog(null, message);
    }

    public String kappaComment(double kappa, DecimalFormat df) {
            if (kappa >= 0.01 && kappa <= 0.40) {
                return "Kappa: " + df.format(kappa) + "--->" + "Zayıf Anlaşma";
            } else if (kappa >= 0.41 && kappa <= 0.60) {
                return "Kappa: " + df.format(kappa) + "--->" + "Orta Anlaşma";
            } else if (kappa >= 0.61 && kappa <= 0.80) {
                return "Kappa: " + df.format(kappa) + "--->" + "İyi Anlaşma";
            } else if (kappa >= 0.81 && kappa <= 1) {
                return "Kappa: " + df.format(kappa) + "--->" + "Çok iyi Anlaşma";
            } else {
                return "Kappa hesaplanamadı";
            }
        }

    public String accuracyComment(double accuracy,DecimalFormat df) {
        if (accuracy >= 70) {
            return "\n" + "Correctly Classified Instances: " + df.format(accuracy) + "%" + "---" + "Kabul edilebilir.";
        } else {
            return "\n" + "Correctly Classified Instances: " + df.format(accuracy) + "%" + "---" + "Kabul edilemez.";
        }
    }

    public String rmseComment(double rmse,DecimalFormat df) {
        if (rmse <= 100) {
            return "\n" + "Root Relative Squared Error: " + df.format(rmse) + "%" + "---" + "İyi bir modele yakın.";

        } else {
           return "\n" + "Root Relative Squared Error: " + df.format(rmse) + "%" + "---" + "Kötü model.";
        }
    }
}

