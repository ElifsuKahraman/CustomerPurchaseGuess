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
        Instances trainingData = new Instances(dataSet, 0, trainSize);
        trainingData.setClassIndex(trainingData.numAttributes() - 1);
        Instances testData = new Instances(dataSet, trainSize, testSize);
        testData.setClassIndex(testData.numAttributes() - 1);
        RandomTree randomTree = new RandomTree();
        randomTree.buildClassifier(trainingData);

        for(int i = 0; i < testData.numInstances(); ++i) {
            double actualClass = testData.instance(i).classValue();
            String actual = testData.classAttribute().value((int)actualClass);
            Instance newInts = testData.instance(i);
            double predNb = randomTree.classifyInstance(newInts);
            String predString = testData.classAttribute().value((int)predNb);
            System.out.println(actual + " " + predString);
        }

        Evaluation eval = new Evaluation(trainingData);
        eval.evaluateModel(randomTree, testData);
        JOptionPane.showMessageDialog(null, eval.toSummaryString("\n Evaulation results: \n", false));
        JOptionPane.showMessageDialog(null, eval.toMatrixString("=== Overall Confisuon Matrix ===\n"));
    }
}
