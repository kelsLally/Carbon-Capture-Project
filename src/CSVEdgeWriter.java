import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVEdgeWriter {
    private String file_name;
    private ILPEdge[] edges;

    public CSVEdgeWriter(String file_name, ILPEdge[] edges) {
        this.file_name = file_name;
        this.edges = edges;
        writeEdgeFile();
    }

    private void writeEdgeFile() {
        try{
            File edge_file = new File(file_name);
            boolean fvar = edge_file.createNewFile();
            if (!fvar) {
                edge_file.delete();
                fvar = edge_file.createNewFile();
            }

            FileWriter csv_edge_writer = new FileWriter(file_name);

            // header info
            csv_edge_writer.append("start");
            csv_edge_writer.append(",");
            csv_edge_writer.append("end");
            csv_edge_writer.append(",");
            csv_edge_writer.append("flow");
            csv_edge_writer.append("\n");

            for (int i = 0; i < edges.length; i ++) {
                csv_edge_writer.append(String.valueOf(edges[i].getStart()));
                csv_edge_writer.append(",");
                csv_edge_writer.append(String.valueOf(edges[i].getEnd()));
                csv_edge_writer.append(",");
                csv_edge_writer.append(String.valueOf(edges[i].getNum_flow()));
                csv_edge_writer.append("\n");
            }

            csv_edge_writer.flush();
            csv_edge_writer.close();

        } catch (IOException e) {
            System.out.println("Exception Occurred:");
            e.printStackTrace();
        }
    }
}
