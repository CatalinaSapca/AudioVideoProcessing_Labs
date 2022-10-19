import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Encoooooder {

    private static int N;
    private static int M;
    private static int maximValue;

    // construieste lista de blocks din matricea Y
    private static List<Block> buildListOfY(double[][] Y) {
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
        return listOfY;
    }

    // construieste lista de blocks din matricea U
    private static List<Block> buildListOfU(double[][] U) {
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
        return listOfU;
    }

    // construieste lista de blocks din matricea V
    private static List<Block> buildListOfV(double[][] V) {
        List<Block> listOfV = new ArrayList<>();

        double[][] localMatrixV = new double[5][5];
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
        return listOfV;
    }

    // face expand la fiecare block din lista matricii U -> din 4X4 in 8X8
    private static List<Block> buildListOfU_Expanded(List<Block> listOfU) {
        List<Block> listOfU_expanded = new ArrayList<>();
        double[][] exp;
        int x, y;

        for (var block : listOfU) {
            x = block.getPositionX();
            y = block.getPositionY();
            exp = expand(block.getBlock());
            Block block1 = new Block(8);
            block1.setBlock(exp);
            block1.setPositionX(x);
            block1.setPositionY(y);
            block1.setType(TypeOfBlock.U);

            listOfU_expanded.add(block1);
        }

        return listOfU_expanded;
    }

    // face expand la fiecare block din lista matricii V -> din 4X4 in 8X8
    private static List<Block> buildListOfV_Expanded(List<Block> listOfV) {
        List<Block> listOfV_expanded = new ArrayList<>();
        double[][] exp;
        int x, y;

        for (var block : listOfV) {
            x = block.getPositionX();
            y = block.getPositionY();
            exp = expand(block.getBlock());
            Block block1 = new Block(8);
            block1.setBlock(exp);
            block1.setPositionX(x);
            block1.setPositionY(y);
            block1.setType(TypeOfBlock.V);

            listOfV_expanded.add(block1);
        }

        return listOfV_expanded;
    }

    private static double[][] expand(double[][] matrix) {
        double[][] expanded = new double[9][9];
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                expanded[2 * (i - 1) + 1][2 * (j - 1) + 1] = matrix[i][j];
                expanded[2 * (i - 1) + 1][2 * j] = matrix[i][j];
                expanded[2 * i][2 * (j - 1) + 1] = matrix[i][j];
                expanded[2 * i][2 * j] = matrix[i][j];
            }
        }


        return expanded;
    }

    private static void subtraction_128(List<Block> listOfBlocks) {
        for (var block : listOfBlocks) {
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 8; j++) {
                    //System.out.print(block.getBlock()[i][j] + " ");
                    block.getBlock()[i][j] = block.getBlock()[i][j] - 128;
                    //System.out.print(block.getBlock()[i][j] + "\n ");
                }
            }
        }
    }

    private static void add_128(List<Block> listOfBlocks) {
        for (var block : listOfBlocks) {
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 8; j++) {
                    //System.out.print(block.getBlock()[i][j] + " ");
                    block.getBlock()[i][j] = block.getBlock()[i][j] + 128;
                    //System.out.print(block.getBlock()[i][j] + "\n ");
                }
            }
        }
    }

    // listOfBlocks --> listOfBlocks_DCT
    private static List<Block> ForwardDCT_transitioning(List<Block> listOfBlocks, TypeOfBlock type) {
        List<Block> listOfBlocks_DCT = new ArrayList<>();
        double[][] DCT_local = new double[9][9];
        int u, v;
        double alfa, beta;
        double suma;
        for (var block : listOfBlocks) {
            //pentru fiecare block
            for (u = 1; u <= 8; u++) {
                for (v = 1; v <= 8; v++) {
                    if (u == 1)
                        alfa = 1 / (Math.sqrt(2D));
                    else
                        alfa = 1;
                    if (v == 1)
                        beta = 1 / (Math.sqrt(2D));
                    else
                        beta = 1;

                    // calculez suma
                    suma = 0;
                    for (int i = 1; i <= 8; i++) {
                        for (int j = 1; j <= 8; j++) {
                            suma = suma + (block.getBlock()[i][j] * Math.cos(((((2 * (i - 1)) + 1) * (u - 1) * (Math.PI)) / 16)) * Math.cos(((((2 * (j - 1)) + 1) * (v - 1) * (Math.PI)) / 16)));
                        }
                    }
                    //valoarea finala din DCT
                    DCT_local[u][v] = 0.25 * alfa * beta * suma;
                    //System.out.println(alfa+" "+beta+" "+ suma+ " "+DCT_local[u][v]);
                }
            }
            Block block1 = new Block(8);
            block1.setPositionX(block.getPositionX());
            block1.setPositionY(block.getPositionY());
            block1.setBlock(DCT_local);
            block1.setType(type);
            listOfBlocks_DCT.add(block1);
        }

        return listOfBlocks_DCT;
    }

    // listOfBlocks_DCT --> listOfBlocks
    private static List<Block> InverseDCT_transitioning(List<Block> listOfBlocks_DCT, TypeOfBlock type) {
        List<Block> listOfBlocks = new ArrayList<>();
        double[][] InverseDCT_local = new double[9][9];
        int x, y;
        int u, v;
        double alfa, beta;
        double suma;
        for (var block : listOfBlocks_DCT) {
            //pentru fiecare block
            for (x = 1; x <= 8; x++) {
                for (y = 1; y <= 8; y++) {
                    // calculez suma

                    suma = 0;
                    for (u = 1; u <= 8; u++) {
                        for (v = 1; v <= 8; v++) {

                            if (u == 1)
                                alfa = 1 / (Math.sqrt(2D));
                            else
                                alfa = 1;
                            if (v == 1)
                                beta = 1 / (Math.sqrt(2D));
                            else
                                beta = 1;

                            suma = suma + (alfa * beta * block.getBlock()[u][v] * Math.cos(((((2 * (x - 1)) + 1) * (u - 1) * Math.PI) / 16)) * Math.cos(((((2 * (y - 1)) + 1) * (v - 1) * Math.PI) / 16)));
                        }
                    }
                    //valoarea finala din InverseDCT
                    InverseDCT_local[x][y] = 0.25 * suma;
                }
            }
            Block block1 = new Block(8);
            block1.setPositionX(block.getPositionX());
            block1.setPositionY(block.getPositionY());
            block1.setBlock(InverseDCT_local);
            block1.setType(type);
            listOfBlocks.add(block1);
        }

        return listOfBlocks;
    }

    private static List<Block> Quantization(List<Block> listOfBlocks, TypeOfBlock type) {
        double[][] Q = {{6, 4, 4, 6, 10, 16, 20, 24}, {5, 5, 6, 8, 10, 23, 24, 22}, {6, 5, 6, 10, 16, 23, 28, 22}, {6, 7, 9, 12, 20, 35, 32, 25}, {7, 9, 15, 22, 27, 44, 41, 31}, {10, 14, 22, 26, 32, 42, 45, 37}, {20, 26, 31, 35, 41, 48, 48, 40}, {29, 37, 38, 39, 45, 40, 41, 40}};

        List<Block> listOfBlocks_Quantized = new ArrayList<>();
        double[][] quantized_local = new double[9][9];
        for (var block : listOfBlocks) {
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 8; j++) {
                    quantized_local[i][j] = (int) (block.getBlock()[i][j] / Q[i - 1][j - 1]);
                }
            }
            Block block1 = new Block(8);
            block1.setPositionX(block.getPositionX());
            block1.setPositionY(block.getPositionY());
            block1.setBlock(quantized_local);
            block1.setType(type);
            listOfBlocks_Quantized.add(block1);
        }

        return listOfBlocks_Quantized;
    }

    private static List<Integer> ZigZag_Encode(Block block) {
        List<Integer> list = new ArrayList<>();

        int index_x, index_y;

        for (int i = 1; i <= 8; i++) {
            if (i % 2 == 0) {
                index_x = 1;
                index_y = i;
                for (int j = 1; j <= i; j++) {
                    //System.out.print((int)block.getBlock()[index_x][index_y] + " ");
                    list.add((int) block.getBlock()[index_x][index_y]);
                    index_x++;
                    index_y--;
                }
            } else {
                index_x = i;
                index_y = 1;
                for (int j = 1; j <= i; j++) {
                    //System.out.print((int)block.getBlock()[index_x][index_y] + " ");
                    list.add((int) block.getBlock()[index_x][index_y]);
                    index_x--;
                    index_y++;
                }
            }
        }

        for (int i = 9; i <= 15; i++) {
            if (i % 2 == 0) {
                index_x = i - 8 + 1;
                index_y = 8;
                for (int j = 1; j <= 2 * 8 - i; j++) {
                    //System.out.print((int)block.getBlock()[index_x][index_y] + " ");
                    list.add((int) block.getBlock()[index_x][index_y]);
                    index_x++;
                    index_y--;
                }
            } else {
                index_x = 8;
                index_y = i - 8 + 1;
                for (int j = 1; j <= 2 * 8 - i; j++) {
                    //System.out.print((int)block.getBlock()[index_x][index_y] + " ");
                    list.add((int) block.getBlock()[index_x][index_y]);
                    index_x--;
                    index_y++;
                }
            }
        }


        return list;
    }

    private static int calculateSize(Integer x) {
        if( x == 0)
            return 2;
        if (x < 0)
            x = x * (-1);

        double result = (Math.log(x) / Math.log(2));
        return (int) result + 2;
    }

    static int[] decimalToBinary(int n, int dim) {
        //System.out.println(n);
        int[] binaryNum = new int[dim];
        for(int i = 0; i <= dim - 1; i++)
            binaryNum[i] = 0;

        if(n < 0) {
            binaryNum[0] = 1;
            n = n * (-1);
        }
        else
            binaryNum[0] = 0;

        int i = dim - 1;
        while (n > 0) {
            binaryNum[i] = n % 2;
            n = n / 2;
            i--;
        }
//        for(int j = 0;j<=dim-1;j++)
//            System.out.print(binaryNum[j]+" ");
//        System.out.println("\n");
        return binaryNum;
    }

    static int binaryToDecimal(List<Byte> listOfBytes, int start, int dim) {
        int num = 0;
        int i;
        int stop = start + dim - 1;
        for(i = start + 1; i<= stop; i++){
            num = (int) (num + listOfBytes.get(i) * Math.pow(2, (stop-i)));
        }
        if(listOfBytes.get(start) == 1)
            num = num * (-1);

        return num;
    }

    private static List<Byte> EntropyEncoding(List<Integer> list) {
        List<Byte> encoded = new ArrayList<>();

        int[] nr_of_zeroes_binary;

        // encodam DC
        int size = calculateSize(list.get(0));
        int[] size_bytes = decimalToBinary(size, 12);
        for(int i = 0; i < 12 ; i++){
            encoded.add((byte) size_bytes[i]);
        }
        int[] amplitude_bytes = decimalToBinary(list.get(0), size);
        for(int i = 0; i < size ; i++){
            encoded.add((byte) amplitude_bytes[i]);
        }

        int nr_of_zeroes = 0;

        for (int i = 1; i < list.size()-1; i++) {
            if(list.get(i) != 0){
                //scriem byte corespunzator RUNLENGTH
                nr_of_zeroes_binary = decimalToBinary(nr_of_zeroes, 12);
                for(int j = 0; j < 12 ; j++){
                    encoded.add((byte) nr_of_zeroes_binary[j]);
                }
                //scriem byte corespunzator SIZE
                size = calculateSize(list.get(i));
                size_bytes = decimalToBinary(size, 12);
                for(int j = 0; j < 12 ; j++){
                    encoded.add((byte) size_bytes[j]);
                }
                //scriem byte corespunzator AMPLITUDe
                amplitude_bytes = decimalToBinary(list.get(i), size);
                for(int j = 0; j < size ; j++){
                    encoded.add((byte) amplitude_bytes[j]);
                }


                nr_of_zeroes = 0;
            }
            else
                nr_of_zeroes++;
        }

        for(int j = 0; j < 24 ; j++){
            encoded.add((byte) 0);
        }

        return encoded;
    }

    private static Block ZigZag_Decode(List<Integer> list, TypeOfBlock type, int x, int y) {
        double[][] matrix = new double[9][9];

        int index_of_list = 0;
        int index_x, index_y;

        for (int i = 1; i <= 8; i++) {
            if (i % 2 == 0) {
                index_x = 1;
                index_y = i;
                for (int j = 1; j <= i; j++) {

                    matrix[index_x][index_y] = list.get(index_of_list);
                    index_of_list++;

                    index_x++;
                    index_y--;
                }
            } else {
                index_x = i;
                index_y = 1;
                for (int j = 1; j <= i; j++) {

                    matrix[index_x][index_y] = list.get(index_of_list);
                    index_of_list++;

                    index_x--;
                    index_y++;
                }
            }
        }

        for (int i = 9; i <= 15; i++) {
            if (i % 2 == 0) {
                index_x = i - 8 + 1;
                index_y = 8;
                for (int j = 1; j <= 2 * 8 - i; j++) {

                    matrix[index_x][index_y] = list.get(index_of_list);
                    index_of_list++;

                    index_x++;
                    index_y--;
                }
            } else {
                index_x = 8;
                index_y = i - 8 + 1;
                for (int j = 1; j <= 2 * 8 - i; j++) {

                    matrix[index_x][index_y] = list.get(index_of_list);
                    index_of_list++;

                    index_x--;
                    index_y++;
                }
            }
        }

        Block block = new Block(8);
        block.setBlock(matrix);
        block.setPositionX(x);
        block.setPositionY(y);
        block.setType(type);

        return block;
    }

    private static List<Block> DeQuantization(List<Block> listOfBlocks_Quantized, TypeOfBlock type) {
        double[][] Q = {{6, 4, 4, 6, 10, 16, 20, 24}, {5, 5, 6, 8, 10, 23, 24, 22}, {6, 5, 6, 10, 16, 23, 28, 22}, {6, 7, 9, 12, 20, 35, 32, 25}, {7, 9, 15, 22, 27, 44, 41, 31}, {10, 14, 22, 26, 32, 42, 45, 37}, {20, 26, 31, 35, 41, 48, 48, 40}, {29, 37, 38, 39, 45, 40, 41, 40}};

        List<Block> listOfBlocks_DeQuantized = new ArrayList<>();
        double[][] deQuantized_local = new double[9][9];
        for (var block : listOfBlocks_Quantized) {
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 8; j++) {
                    deQuantized_local[i][j] = block.getBlock()[i][j] * Q[i - 1][j - 1];
                }
            }
            Block block1 = new Block(8);
            block1.setPositionX(block.getPositionX());
            block1.setPositionY(block.getPositionY());
            block1.setBlock(deQuantized_local);
            block1.setType(type);
            listOfBlocks_DeQuantized.add(block1);
        }

        return listOfBlocks_DeQuantized;
    }

    private static void matrix_reconstruction(List<Block> listOfBlocks, double[][] builtMatrix) {
        // construim matricea
        int a, b, i, j, x, y;
        for (var block : listOfBlocks) {
            x = block.getPositionX();
            y = block.getPositionY();
            a = 1;
            for (i = (x - 1) * 8 + 1; i <= x * 8; i++) {
                b = 1;
                for (j = (y - 1) * 8 + 1; j <= y * 8; j++) {
                    builtMatrix[i][j] = block.getBlock()[a][b];
                    b++;
                }
                a++;
            }
        }
    }

    private static void RGB_conversion(double[][] Y, double[][] U, double[][] V, double[][] R, double[][] G, double[][] B) {
        int i, j;
        i = 1;
        while (i <= N) {
            j = 1;
            while (j <= M) {
                R[i][j] = 1.140 * V[i][j] + Y[i][j];
                G[i][j] = Y[i][j] - 0.395 * U[i][j] - 0.581 * V[i][j];
                B[i][j] = Y[i][j] + 2.032 * U[i][j];

                j++;
            }
            i++;
        }
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
        for (var block : listOfV) {
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

    private static void writeToImage(double[][] R, double[][] G, double[][] B) throws IOException {
        // afisare in fisier

        BufferedWriter writer = new BufferedWriter(new FileWriter("nt2-P3.ppm"));
        writer.write("P3\n");
        writer.write("# CREATOR: GIMP PNM Filter Version 1.1\n");
        writer.write(M + " " + N + "\n");
        writer.write(maximValue + "\n");

        int i, j;
        for (i = 1; i <= N; i++) {
            for (j = 1; j <= M; j++) {

                if (R[i][j] > 255)
                    R[i][j] = 255;
                if (G[i][j] > 255)
                    G[i][j] = 255;
                if (B[i][j] > 255)
                    B[i][j] = 255;

                if (R[i][j] < 0)
                    R[i][j] = 0;
                if (G[i][j] < 0)
                    G[i][j] = 0;
                if (B[i][j] < 0)
                    B[i][j] = 0;

                writer.write((int) Math.round(R[i][j]) + "\n");
                writer.write((int) Math.round(G[i][j]) + "\n");
                writer.write((int) Math.round(B[i][j]) + "\n");

            }
        }
        writer.close();
    }

    private static void readImage(String fileName, double[][] Y, double[][] U, double[][] V) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line = bufferedReader.readLine();
            line = bufferedReader.readLine();
            while (line.contains("#")) {
                line = bufferedReader.readLine();
            }

            String[] splitLine = line.split(" ");
            // citim dimensiunile matricii
            // N - latime
            M = Integer.parseInt(splitLine[0]);
            // M - inaltime
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
        // --------------------------------------------------------------------------------- the encoder part
        double[][] Y = new double[1000][1000];
        double[][] U = new double[1000][1000];
        double[][] V = new double[1000][1000];
        Encoooooder.readImage("nt-P3.ppm", Y, U, V);
        System.out.println(N);
        System.out.println(M);

        //lista de blocks apartinand matricii Y
        List<Block> listOfY = buildListOfY(Y);

        //lista de blocks apartinand matricii U
        List<Block> listOfU = buildListOfU(U);

        //lista de blocks apartinand matricii V
        List<Block> listOfV = buildListOfV(V);

        //writeToFile(listOfY, listOfU, listOfV);

        // ------------------------------------------------------------------------------------- expanding the U, V blocks to 8X8

        listOfU = buildListOfU_Expanded(listOfU);
        listOfV = buildListOfV_Expanded(listOfV);

        // ------------------------------------------------------------------------------------- subtracting 128 from each value of every 8X8 block Y/Cr/Cb

        subtraction_128(listOfY);
        subtraction_128(listOfU);
        subtraction_128(listOfV);

        // ------------------------------------------------------------------------------------- Forward DCT

        listOfY = ForwardDCT_transitioning(listOfY, TypeOfBlock.Y);
        listOfU = ForwardDCT_transitioning(listOfU, TypeOfBlock.U);
        listOfV = ForwardDCT_transitioning(listOfV, TypeOfBlock.V);

        // ------------------------------------------------------------------------------------- Quantization

        listOfY = Quantization(listOfY, TypeOfBlock.Y);
        listOfU = Quantization(listOfU, TypeOfBlock.U);
        listOfV = Quantization(listOfV, TypeOfBlock.V);

        // ------------------------------------------------------------------------------------- Entropy Encoding

        List<Byte> EntropyEncoded = new ArrayList<>();

        //unde listOfY.size() = listOfU.size() = listOfV.size() = 7500 blocks of 8X8
        for (int i = 0; i < listOfY.size(); i++) {
            Block blockOfY = listOfY.get(i);
            Block blockOfU = listOfU.get(i);
            Block blockOfV = listOfV.get(i);
            List<Integer> Y_ZigZag = ZigZag_Encode(blockOfY);
            List<Integer> U_ZigZag = ZigZag_Encode(blockOfU);
            List<Integer> V_ZigZag = ZigZag_Encode(blockOfV);

            EntropyEncoded.addAll(EntropyEncoding(Y_ZigZag));
            EntropyEncoded.addAll(EntropyEncoding(U_ZigZag));
            EntropyEncoded.addAll(EntropyEncoding(V_ZigZag));
        }


        // ------------------------------------------------------------------------------------- Entropy Decoding

        //System.out.println(EntropyEncoded);

        List<Block> listOfY_EntropyDecoded = new ArrayList<>();
        List<Block> listOfU_EntropyDecoded = new ArrayList<>();
        List<Block> listOfV_EntropyDecoded = new ArrayList<>();

        int index = 0;
        int blockLocationX, blockLocationY;
        blockLocationX = 1;
        blockLocationY = 1;
        int dc_size, dc_amplitude, ac_size, ac_amplitude, ac_runlength;

        while(index < EntropyEncoded.size() - 1) {
            List<Integer> Y_ZigZag = new ArrayList<>();
            List<Integer> U_ZigZag = new ArrayList<>();
            List<Integer> V_ZigZag = new ArrayList<>();

            // pentru Y
            dc_size = binaryToDecimal(EntropyEncoded, index, 12);
            index  = index + 12;
            dc_amplitude = binaryToDecimal(EntropyEncoded, index, dc_size);
            index = index + dc_size;
            Y_ZigZag.add(dc_amplitude);

            boolean ok = true;
            while(ok){
                ac_runlength = binaryToDecimal(EntropyEncoded, index, 12);
                index  = index + 12;
                ac_size = binaryToDecimal(EntropyEncoded, index, 12);
                index  = index + 12;

                if(ac_size != 0){
                    ac_amplitude = binaryToDecimal(EntropyEncoded, index, ac_size);
                    index = index + ac_size;

                    if(ac_runlength != 0){
                        for(int q = 1; q<= ac_runlength; q++)
                            Y_ZigZag.add(0);
                        Y_ZigZag.add(ac_amplitude);
                    }
                    else
                        Y_ZigZag.add(ac_amplitude);
                }
                else
                    ok = false;
            }
            int l = Y_ZigZag.size();
            for(int ll = l+1 ;ll<=64;ll++)
                Y_ZigZag.add(0);
            listOfY_EntropyDecoded.add(ZigZag_Decode(Y_ZigZag, TypeOfBlock.Y, blockLocationX, blockLocationY));

            // pentru U
            dc_size = binaryToDecimal(EntropyEncoded, index, 12);
            index  = index + 12;
            dc_amplitude = binaryToDecimal(EntropyEncoded, index, dc_size);
            index = index + dc_size;
            U_ZigZag.add(dc_amplitude);

            ok = true;
            while(ok){
                ac_runlength = binaryToDecimal(EntropyEncoded, index, 12);
                index  = index + 12;
                ac_size = binaryToDecimal(EntropyEncoded, index, 12);
                index  = index + 12;

                if(ac_size != 0){
                    ac_amplitude = binaryToDecimal(EntropyEncoded, index, ac_size);
                    index = index + ac_size;

                    if(ac_runlength != 0){
                        for(int q = 1; q<= ac_runlength; q++)
                            U_ZigZag.add(0);
                        U_ZigZag.add(ac_amplitude);
                    }
                    else
                        U_ZigZag.add(ac_amplitude);
                }
                else
                    ok = false;
            }
            l = U_ZigZag.size();
            for(int ll = l+1 ;ll<=64;ll++)
                U_ZigZag.add(0);
            listOfU_EntropyDecoded.add(ZigZag_Decode(U_ZigZag, TypeOfBlock.U, blockLocationX, blockLocationY));

            // pentru V
            dc_size = binaryToDecimal(EntropyEncoded, index, 12);
            index  = index + 12;
            dc_amplitude = binaryToDecimal(EntropyEncoded, index, dc_size);
            index = index + dc_size;
            V_ZigZag.add(dc_amplitude);

            ok = true;
            while(ok){
                ac_runlength = binaryToDecimal(EntropyEncoded, index, 12);
                index  = index + 12;
                ac_size = binaryToDecimal(EntropyEncoded, index, 12);
                index  = index + 12;

                if(ac_size != 0){
                    ac_amplitude = binaryToDecimal(EntropyEncoded, index, ac_size);
                    index = index + ac_size;

                    if(ac_runlength != 0){
                        for(int q = 1; q<= ac_runlength; q++)
                            V_ZigZag.add(0);
                        V_ZigZag.add(ac_amplitude);
                    }
                    else
                        V_ZigZag.add(ac_amplitude);
                }
                else
                    ok = false;
            }
            l = V_ZigZag.size();
            for(int ll = l+1 ;ll<=64;ll++)
                V_ZigZag.add(0);
            listOfV_EntropyDecoded.add(ZigZag_Decode(V_ZigZag, TypeOfBlock.V, blockLocationX, blockLocationY));

            if(blockLocationY == 100){
                blockLocationX++;
                blockLocationY = 1;
            }
            else{
                blockLocationY++;
            }
        }

        // ------------------------------------------------------------------------------------- DeQuantization

        listOfY = DeQuantization(listOfY_EntropyDecoded, TypeOfBlock.Y);
        listOfU = DeQuantization(listOfU_EntropyDecoded, TypeOfBlock.U);
        listOfV = DeQuantization(listOfV_EntropyDecoded, TypeOfBlock.V);

        // ------------------------------------------------------------------------------------- Inverse DCT

        listOfY = InverseDCT_transitioning(listOfY, TypeOfBlock.Y);
        listOfU = InverseDCT_transitioning(listOfU, TypeOfBlock.U);
        listOfV = InverseDCT_transitioning(listOfV, TypeOfBlock.V);

        // ------------------------------------------------------------------------------------- adding 128 to each value of every 8X8 block Y/Cr/Cb

        add_128(listOfY);
        add_128(listOfU);
        add_128(listOfV);

        // ------------------------------------------------------------------------------------- matrix reconstruction

        double[][] matrixY = new double[1000][1000];
        double[][] matrixU = new double[1000][1000];
        double[][] matrixV = new double[1000][1000];

        matrix_reconstruction(listOfY, matrixY);
        matrix_reconstruction(listOfU, matrixU);
        matrix_reconstruction(listOfV, matrixV);

        // ------------------------------------------------------------------------------------- RGB conversion

        double[][] R = new double[1000][1000];
        double[][] G = new double[1000][1000];
        double[][] B = new double[1000][1000];

        RGB_conversion(matrixY, matrixU, matrixV, R, G, B);

        // ------------------------------------------------------------------------------------- writing to image

        writeToImage(R, G, B);

    }
}
