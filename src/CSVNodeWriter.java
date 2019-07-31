import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVNodeWriter {
    private String file_name;
    private Node[] nodes;

    public CSVNodeWriter(String file_name, Node[] nodes) {
        this.file_name = file_name;
        this.nodes = nodes;
        writeNodeFile();
    }

    private void writeNodeFile () {
        try {
            File node_file = new File(file_name);
            boolean fvar = node_file.createNewFile();
            if (!fvar) {
                node_file.delete();
                fvar = node_file.createNewFile();
            }

            FileWriter csv_node_writer = new FileWriter(file_name);

            csv_node_writer.append("x_coordinate");
            csv_node_writer.append(",");
            csv_node_writer.append("y_coordinate");
            csv_node_writer.append(",");
            csv_node_writer.append("node_type");
            csv_node_writer.append("\n");

            for (Node n: nodes) {
                csv_node_writer.append(String.valueOf(n.getX()));
                csv_node_writer.append(",");
                csv_node_writer.append(String.valueOf(n.getY()));
                csv_node_writer.append(",");
                csv_node_writer.append(String.valueOf(n.getNode_type()));
                csv_node_writer.append("\n");
            }

            csv_node_writer.flush();
            csv_node_writer.close();

        } catch (IOException e) {
            System.out.println("Exception Occurred:");
            e.printStackTrace();
        }
    }
}
