package students_dorms;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
        
public class PaymentStatisticsChart extends JFrame{
    JButton btn;
    public PaymentStatisticsChart(String title) {
        super(title);
        setBounds(400,120,700,500);
        setLayout(null);
        btn = new JButton("Exit");
        btn.setBounds(500,0,100,100);
        add(btn);
        setUndecorated(true);
        setVisible(true);

        // Create dataset
        DefaultPieDataset dataset = new DefaultPieDataset();

        

        try {
            Connection con = conn.getCon();
            Statement statement = con.createStatement();

            // Query to get the total number of students
            String totalStudentsQuery = "SELECT COUNT(*) AS total FROM students";
            ResultSet totalStudentsResult = statement.executeQuery(totalStudentsQuery);
            totalStudentsResult.next();
            int totalStudents = totalStudentsResult.getInt("total");

            // Query to get the number of students who have paid
            String paidStudentsQuery = "SELECT COUNT(DISTINCT id_invoice) AS paid FROM fees";
            ResultSet paidStudentsResult = statement.executeQuery(paidStudentsQuery);
            paidStudentsResult.next();
            int paidStudents = paidStudentsResult.getInt("paid");

            // Calculate the numbers
            int unpaidStudents = totalStudents - paidStudents;

            // Create dataset
            dataset.setValue("Paid", paidStudents);
            dataset.setValue("Unpaid", unpaidStudents);

            // Create chart
            JFreeChart chart = ChartFactory.createPieChart(
                    "Student Payment Statistics",
                    dataset,
                    true,
                    true,
                    false
            );

            // Customize chart appearance
            PiePlot plot = (PiePlot) chart.getPlot();
            plot.setLabelGenerator(new org.jfree.chart.labels.StandardPieSectionLabelGenerator("{0}: {1} ({2})"));

            // Add chart to a panel
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(560, 370));
            setContentPane(chartPanel);

            // Close resources
            statement.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PaymentStatisticsChart example = new PaymentStatisticsChart("Student Payment Statistics");
            example.setSize(800, 600);
            example.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            example.setVisible(true);
        });
    }
}
