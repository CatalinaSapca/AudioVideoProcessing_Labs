import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EncoderAndDecoder {

    private static int N;
    private static int M;
    private static int maximValue;


    private static double[][] expand(double[][] matrix) {
        double[][] expanded = new double[9][9];
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                expanded[2*(i-1)+1][2*(j-1)+1] = matrix[i][j];
                expanded[2*(i-1)+1][2*j] = matrix[i][j];
                expanded[2*i][2*(j-1)+1] = matrix[i][j];
                expanded[2*i][2*j] = matrix[i][j];
            }
        }


        return expanded;
    }

    private static void writeToFile(List<Block> listOfY, List<Block> listOfU, List<Block> listOfV) throws IOException {
        //scriem blocks din Y
        BufferedWriter writer = new BufferedWriter(new FileWriter("outputEncoderY.txt"));
        int a = 1;
        for (var block : listOfY) {
            writer.write("Sunt block nr " + a + "\n");
            writer.write("Pozitia " + block.getPositionX() + " " + block.getPositionY() + "\n");
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 8; j++)
                    writer.write((int) block.getBlock()[i][j] + " ");
                writer.write("\n");
            }
            writer.write("\n");

            a++;
        }

        writer.close();

        // scriem blocks din U
        BufferedWriter writerU = new BufferedWriter(new FileWriter("outputEncoderU.txt"));
        a = 1;
        for (var block : listOfU) {
            writerU.write("Sunt block nr " + a + "\n");
            writerU.write("Pozitia " + block.getPositionX() + " " + block.getPositionY() + "\n");
            for (int i = 1; i <= 4; i++) {
                for (int j = 1; j <= 4; j++)
                    writerU.write((int) block.getBlock()[i][j] + " ");
                writerU.write("\n");
            }
            writerU.write("\n");

            a++;
        }

        writerU.close();

        // scriem blocks din V
        BufferedWriter writerV = new BufferedWriter(new FileWriter("outputEncoderV.txt"));
        a = 1;
        for (var block : listOfU) {
            writerV.write("Sunt block nr " + a + "\n");
            writerV.write("Pozitia " + block.getPositionX() + " " + block.getPositionY() + "\n");
            for (int i = 1; i <= 4; i++) {
                for (int j = 1; j <= 4; j++)
                    writerV.write((int) block.getBlock()[i][j] + " ");
                writerV.write("\n");
            }
            writerV.write("\n");

            a++;
        }

        writerV.close();
    }

    private static void readImage(String fileName, double[][] Y, double[][] U, double[][] V) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line = bufferedReader.readLine();
            line = bufferedReader.readLine();
            while (line.contains("#")) {
                line = bufferedReader.readLine();
            }

            String[] splitLine = line.split(" ");
            // citim dimensiunile matricii
            // N - latime - 800
            M = Integer.parseInt(splitLine[0]);
            // M - inaltime - 600
            N = Integer.parseInt(splitLine[1]);

            line = bufferedReader.readLine();
            maximValue = Integer.parseInt(line);

            int i = 1;
            int R, G, B;
            while (i <= N) {
                int j = 1;
                while (j <= M) {
                    R = Integer.parseInt(bufferedReader.readLine());
                    G = Integer.parseInt(bufferedReader.readLine());
                    B = Integer.parseInt(bufferedReader.readLine());

//                    Y[i][j] = 0.299 * R + 0.587 * G + 0.114 * B + 0;
//                    U[i][j] = -0.169 * R - 0.331 * G + 0.499 * B + 128;
//                    V[i][j] = 0.499 * R - 0.418 * G - 0.0813 * B + 128;
                    Y[i][j] = 0.299 * R + 0.587 * G + 0.114 * B + 0;
                    U[i][j] = -0.174 * R - 0.289 * G + 0.436 * B;
                    V[i][j] = 0.615 * R - 0.515 * G - 0.100 * B;

                    j++;
                }
                i++;
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("sdgfwdsfg");
        }
    }

    public static void main(String[] args) throws IOException {
        // the encoder part
        double[][] Y = new double[1000][1000];
        double[][] U = new double[1000][1000];
        double[][] V = new double[1000][1000];
        EncoderAndDecoder.readImage("nt-P3.ppm", Y, U, V);
        System.out.println(N);
        System.out.println(M);

        //lista de blocks apartinand matricii Y
        List<Block> listOfY = new ArrayList<>();

        double[][] localMatrixY = new double[9][9];

        for (int i = 1; i <= N / 8; i++) {
            for (int j = 1; j <= M / 8; j++) {
                Block block = new Block(8);
                int m, n;
                m = 1;
                for (int q = (i - 1) * 8 + 1; q <= i * 8; q++) {
                    n = 1;
                    for (int w = (j - 1) * 8 + 1; w <= j * 8; w++) {
                        localMatrixY[m][n] = Y[q][w];
                        n++;
                    }
                    m++;
                }

                block.setBlock(localMatrixY);
                block.setPositionX(i);
                block.setPositionY(j);
                block.setType(TypeOfBlock.Y);

                listOfY.add(block);
            }
        }

        //lista de blocks apartinand matricii U
        List<Block> listOfU = new ArrayList<>();

        double[][] localMatrixU = new double[5][5];
        double suma;

        int i, j, x, y, q, w;
        for (i = 1; i <= N / 8; i++) {
            for (j = 1; j <= M / 8; j++) {
                Block block = new Block(4);
                for (x = 1; x <= 4; x++) {
                    for (y = 1; y <= 4; y++) {
                        suma = 0;
                        for (q = (i - 1) * 8 + 1 + 2 * (x - 1); q <= (i - 1) * 8 + 1 + 2 * x - 1; q++) {
                            for (w = (j - 1) * 8 + 1 + 2 * (y - 1); w <= (j - 1) * 8 + 1 + 2 * y - 1; w++) {
                                suma = suma + U[q][w];
                            }
                        }
                        localMatrixU[x][y] = suma / 4;
                    }
                }

                block.setBlock(localMatrixU);
                block.setPositionX(i);
                block.setPositionY(j);
                block.setType(TypeOfBlock.U);

                listOfU.add(block);
            }
        }

        //lista de blocks apartinand matricii V
        List<Block> listOfV = new ArrayList<>();


        double[][] localMatrixV = new double[5][5];

        for (i = 1; i <= N / 8; i++) {
            for (j = 1; j <= M / 8; j++) {
                Block block = new Block(4);
                for (x = 1; x <= 4; x++) {
                    for (y = 1; y <= 4; y++) {
                        suma = 0;
                        for (q = (i - 1) * 8 + 1 + 2 * (x - 1); q <= (i - 1) * 8 + 1 + 2 * x - 1; q++) {
                            for (w = (j - 1) * 8 + 1 + 2 * (y - 1); w <= (j - 1) * 8 + 1 + 2 * y - 1; w++) {
                                suma = suma + V[q][w];
                            }
                        }
                        localMatrixV[x][y] = suma / 4;
                    }
                }

                block.setBlock(localMatrixV);
                block.setPositionX(i);
                block.setPositionY(j);
                block.setType(TypeOfBlock.V);

                listOfV.add(block);
            }
        }

        writeToFile(listOfY, listOfU, listOfV);

        // the decoder part
        double[][] YDecoded = new double[1000][1000];
        double[][] UDecoded = new double[1000][1000];
        double[][] VDecoded = new double[1000][1000];

        // construim matricea Y
        int a, b;
        for (var block : listOfY) {
            x = block.getPositionX();
            y = block.getPositionY();
            a = 1;
            for (i = (x - 1) * 8 + 1; i <= x * 8; i++) {
                b = 1;
                for (j = (y - 1) * 8 + 1; j <= y * 8; j++) {
                    YDecoded[i][j] = block.getBlock()[a][b];
                    b++;
                }
                a++;
            }
        }

        double[][] expanded;
        // construim matricea U
        for (var block : listOfU) {
            x = block.getPositionX();
            y = block.getPositionY();
            expanded = expand(block.getBlock());
            a = 1;
            for (i = (x - 1) * 8 + 1; i <= x * 8; i++) {
                b = 1;
                for (j = (y - 1) * 8 + 1; j <= y * 8; j++) {
                    UDecoded[i][j] = expanded[a][b];
                    b++;
                }
                a++;
            }
        }

        // construim matricea V
        for (var block : listOfV) {
            x = block.getPositionX();
            y = block.getPositionY();
            expanded = expand(block.getBlock());
            a = 1;
            for (i = (x - 1) * 8 + 1; i <= x * 8; i++) {
                b = 1;
                for (j = (y - 1) * 8 + 1; j <= y * 8; j++) {
                    VDecoded[i][j] = expanded[a][b];
                    b++;
                }
                a++;
            }
        }

        double[][] R = new double[1000][1000];
        double[][] G = new double[1000][1000];
        double[][] B = new double[1000][1000];

        i = 1;
        double YYY, UUU, VVV;
        while (i <= N) {
            j = 1;
            while (j <= M) {
                YYY = YDecoded[i][j] - 16;
                UUU = UDecoded[i][j] - 128;
                VVV = VDecoded[i][j] - 128;
//                R[i][j] = 1.164 * YYY + 1.596 * VVV;
//                G[i][j] = 1.164 * YYY - 0.391 * UUU - 0.813 * VVV;
//                B[i][j] = 1.164 * YYY + 2.018 * UUU;
                R[i][j] = 1.140 * VDecoded[i][j] + YDecoded[i][j];
                G[i][j] = YDecoded[i][j] - 0.395*UDecoded[i][j] - 0.581*VDecoded[i][j];
                B[i][j] = YDecoded[i][j] + 2.032*UDecoded[i][j];

                if(R[i][j]>255)
                    R[i][j] = 255;
                if(G[i][j]>255)
                    G[i][j] = 255;
                if(B[i][j]>255)
                    B[i][j] = 255;

                if(R[i][j]<0)
                    R[i][j] = 0;
                if(G[i][j]<0)
                    G[i][j] = 0;
                if(B[i][j]<0)
                    B[i][j] = 0;


                j++;
            }
            i++;
        }

        // afisare in fisier

        BufferedWriter writer = new BufferedWriter(new FileWriter("nt2-P3.ppm"));
        writer.write("P3\n");
        writer.write("# CREATOR: GIMP PNM Filter Version 1.1\n");
        writer.write(M + " " + N + "\n");
        writer.write(maximValue + "\n");

        for(i = 1; i <= N; i++){
            for(j = 1; j <= M; j++){
                writer.write((int)Math.round(R[i][j]) + "\n");
                writer.write((int)Math.round(G[i][j]) + "\n");
                writer.write((int)Math.round(B[i][j]) + "\n");
            }
        }
        writer.close();






//        BufferedWriter writerR = new BufferedWriter(new FileWriter("a.txt"));
//        for(i = 1; i <= M; i++){
//            for(j = 1; j <= N; j++){
//                writerR.write((int)Y[i][j]+" ");
//            }
//            writerR.write("\n");
//        }
//        writerR.close();
//
//        BufferedWriter writerW = new BufferedWriter(new FileWriter("b.txt"));
//        for(i = 1; i <= M; i++){
//            for(j = 1; j <= N; j++){
//                writerW.write((int)YDecoded[i][j]+" ");
//            }
//            writerW.write("\n");
//        }
//        writerW.close();
    }
}
