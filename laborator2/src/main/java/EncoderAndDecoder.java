import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EncoderAndDecoder {

    private static int N;
    private static int M;
    private static int maximValue;

    // construieste lista de blocks din matricea Y
    private static List<Block> buildListOfY(double[][] Y){
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
    private static List<Block> buildListOfU(double[][] U){
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
    private static List<Block> buildListOfV(double[][] V){
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
    private static List<Block> buildListOfU_Expanded(List<Block> listOfU){
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
    private static List<Block> buildListOfV_Expanded(List<Block> listOfV){
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
                expanded[2*(i-1)+1][2*(j-1)+1] = matrix[i][j];
                expanded[2*(i-1)+1][2*j] = matrix[i][j];
                expanded[2*i][2*(j-1)+1] = matrix[i][j];
                expanded[2*i][2*j] = matrix[i][j];
            }
        }


        return expanded;
    }

    private static void subtraction_128(List<Block> listOfBlocks) {
        for (var block : listOfBlocks) {
            for(int i=1; i<=8;i++){
                for(int j=1;j<=8;j++){
                    //System.out.print(block.getBlock()[i][j] + " ");
                    block.getBlock()[i][j] = block.getBlock()[i][j] - 128;
                    //System.out.print(block.getBlock()[i][j] + "\n ");
                }
            }
        }
    }

    private static void add_128(List<Block> listOfBlocks) {
        for (var block : listOfBlocks) {
            for(int i=1; i<=8;i++){
                for(int j=1;j<=8;j++){
                    //System.out.print(block.getBlock()[i][j] + " ");
                    block.getBlock()[i][j] = block.getBlock()[i][j] + 128;
                    //System.out.print(block.getBlock()[i][j] + "\n ");
                }
            }
        }
    }

    public static void cleanMatrxi(double[][] matrix){
        for(int i = 1;i<=8;i++)
            for(int j =1;j<=8;j++)
                matrix[i][j] = 0;
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
            for(u=1; u<=8; u++){
                for(v=1; v<=8; v++){
                    if(u == 1)
                        alfa = 1/(Math.sqrt(2D));
                    else
                        alfa = 1;
                    if(v == 1)
                        beta = 1/(Math.sqrt(2D));
                    else
                        beta = 1;

                    // calculez suma
                    suma = 0;
                    for(int i=1; i<=8; i++){
                        for(int j=1; j<=8; j++){
                            suma = suma + (block.getBlock()[i][j] * Math.cos(((((2*(i-1))+1)*(u-1)*(Math.PI))/16)) * Math.cos(((((2*(j-1))+1)*(v-1)*(Math.PI))/16)));
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
            for(x = 1;x<=8;x++){
                for(y=1; y<=8; y++){
                    // calculez suma

                    suma = 0;
                    for(u=1; u<=8; u++){
                        for(v=1; v<=8; v++){

                            if(u == 1)
                                alfa = 1/(Math.sqrt(2D));
                            else
                                alfa = 1;
                            if(v == 1)
                                beta = 1/(Math.sqrt(2D));
                            else
                                beta = 1;

                            suma = suma + (alfa * beta * block.getBlock()[u][v] * Math.cos(((((2*(x-1))+1)*(u-1)*Math.PI)/16)) * Math.cos(((((2*(y-1))+1)*(v-1)*Math.PI)/16)));
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
        double[][] Q = {{6,4,4,6,10,16,20,24},{5,5,6,8,10,23,24,22},{6,5,6,10,16,23,28,22},{6,7,9,12,20,35,32,25},{7,9,15,22,27,44,41,31},{10,14,22,26,32,42,45,37},{20,26,31,35,41,48,48,40},{29,37,38,39,45,40,41,40}};

        List<Block> listOfBlocks_Quantized = new ArrayList<>();
        double[][] quantized_local = new double[9][9];
        for (var block : listOfBlocks) {
            for(int i=1; i<=8; i++){
                for(int j=1; j<=8; j++){
                    quantized_local[i][j] = (int)(block.getBlock()[i][j]/Q[i-1][j-1]);
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

    private static List<Block> DeQuantization(List<Block> listOfBlocks_Quantized, TypeOfBlock type) {
        double[][] Q = {{6,4,4,6,10,16,20,24},{5,5,6,8,10,23,24,22},{6,5,6,10,16,23,28,22},{6,7,9,12,20,35,32,25},{7,9,15,22,27,44,41,31},{10,14,22,26,32,42,45,37},{20,26,31,35,41,48,48,40},{29,37,38,39,45,40,41,40}};

        List<Block> listOfBlocks_DeQuantized = new ArrayList<>();
        double[][] deQuantized_local = new double[9][9];
        for (var block : listOfBlocks_Quantized) {
            for(int i=1; i<=8; i++){
                for(int j=1; j<=8; j++){
                    deQuantized_local[i][j] = block.getBlock()[i][j] * Q[i-1][j-1];
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
                G[i][j] = Y[i][j] - 0.395*U[i][j] - 0.581*V[i][j];
                B[i][j] = Y[i][j] + 2.032*U[i][j];

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
        for(i = 1; i <= N; i++){
            for(j = 1; j <= M; j++){

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

                writer.write((int)Math.round(R[i][j]) + "\n");
                writer.write((int)Math.round(G[i][j]) + "\n");
                writer.write((int)Math.round(B[i][j]) + "\n");

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
        EncoderAndDecoder.readImage("nt-P3.ppm", Y, U, V);
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

        List<Block> listOfU_expanded = buildListOfU_Expanded(listOfU);
        List<Block> listOfV_expanded = buildListOfV_Expanded(listOfV);

        // ------------------------------------------------------------------------------------- subtracting 128 from each value of every 8X8 block Y/Cr/Cb

        subtraction_128(listOfY);
        subtraction_128(listOfU_expanded);
        subtraction_128(listOfV_expanded);

        // ------------------------------------------------------------------------------------- Forward DCT

        List<Block> listOfY_DCT = ForwardDCT_transitioning(listOfY, TypeOfBlock.Y);
        List<Block> listOfU_DCT = ForwardDCT_transitioning(listOfU_expanded, TypeOfBlock.U);
        List<Block> listOfV_DCT = ForwardDCT_transitioning(listOfV_expanded, TypeOfBlock.V);

        // ------------------------------------------------------------------------------------- Quantization

        List<Block> listOfY_Quantized = Quantization(listOfY_DCT, TypeOfBlock.Y);
        List<Block> listOfU_Quantized = Quantization(listOfU_DCT, TypeOfBlock.U);
        List<Block> listOfV_Quantized = Quantization(listOfV_DCT, TypeOfBlock.V);

        // ------------------------------------------------------------------------------------- DeQuantization

        List<Block> listOfY_DeQuantized = DeQuantization(listOfY_Quantized, TypeOfBlock.Y);
        List<Block> listOfU_DeQuantized = DeQuantization(listOfU_Quantized, TypeOfBlock.U);
        List<Block> listOfV_DeQuantized = DeQuantization(listOfV_Quantized, TypeOfBlock.V);

        // ------------------------------------------------------------------------------------- Inverse DCT

        List<Block> listOfY_InverseDCT = InverseDCT_transitioning(listOfY_DeQuantized, TypeOfBlock.Y);
        List<Block> listOfU_InverseDCT = InverseDCT_transitioning(listOfU_DeQuantized, TypeOfBlock.U);
        List<Block> listOfV_InverseDCT = InverseDCT_transitioning(listOfV_DeQuantized, TypeOfBlock.V);

        // ------------------------------------------------------------------------------------- adding 128 to each value of every 8X8 block Y/Cr/Cb

        add_128(listOfY_InverseDCT);
        add_128(listOfU_InverseDCT);
        add_128(listOfV_InverseDCT);

        // ------------------------------------------------------------------------------------- matrix reconstruction

        double[][] matrixY = new double[1000][1000];
        double[][] matrixU = new double[1000][1000];
        double[][] matrixV = new double[1000][1000];

        matrix_reconstruction(listOfY_InverseDCT, matrixY);
        matrix_reconstruction(listOfU_InverseDCT, matrixU);
        matrix_reconstruction(listOfV_InverseDCT, matrixV);

        // ------------------------------------------------------------------------------------- RGB conversion

        double[][] R = new double[1000][1000];
        double[][] G = new double[1000][1000];
        double[][] B = new double[1000][1000];

        RGB_conversion(matrixY, matrixU, matrixV, R, G, B);

        // ------------------------------------------------------------------------------------- writing to image

        writeToImage(R, G, B);

//        // ------------------------------------------------------------------------------------- the decoder part
//        double[][] YDecoded = new double[1000][1000];
//        double[][] UDecoded = new double[1000][1000];
//        double[][] VDecoded = new double[1000][1000];
//
//        // construim matricea Y
//        int a, b, i, j, x, y;
//        for (var block : listOfY) {
//            x = block.getPositionX();
//            y = block.getPositionY();
//            a = 1;
//            for (i = (x - 1) * 8 + 1; i <= x * 8; i++) {
//                b = 1;
//                for (j = (y - 1) * 8 + 1; j <= y * 8; j++) {
//                    YDecoded[i][j] = block.getBlock()[a][b];
//                    b++;
//                }
//                a++;
//            }
//        }
//
//        double[][] expanded;
//        // construim matricea U
//        for (var block : listOfU) {
//            x = block.getPositionX();
//            y = block.getPositionY();
//            expanded = expand(block.getBlock());
//            a = 1;
//            for (i = (x - 1) * 8 + 1; i <= x * 8; i++) {
//                b = 1;
//                for (j = (y - 1) * 8 + 1; j <= y * 8; j++) {
//                    UDecoded[i][j] = expanded[a][b];
//                    b++;
//                }
//                a++;
//            }
//        }
//
//        // construim matricea V
//        for (var block : listOfV) {
//            x = block.getPositionX();
//            y = block.getPositionY();
//            expanded = expand(block.getBlock());
//            a = 1;
//            for (i = (x - 1) * 8 + 1; i <= x * 8; i++) {
//                b = 1;
//                for (j = (y - 1) * 8 + 1; j <= y * 8; j++) {
//                    VDecoded[i][j] = expanded[a][b];
//                    b++;
//                }
//                a++;
//            }
//        }
//
//        double[][] R = new double[1000][1000];
//        double[][] G = new double[1000][1000];
//        double[][] B = new double[1000][1000];
//
//        i = 1;
//        double YYY, UUU, VVV;
//        while (i <= M) {
//            j = 1;
//            while (j <= N) {
//                YYY = YDecoded[i][j] - 16;
//                UUU = UDecoded[i][j] - 128;
//                VVV = VDecoded[i][j] - 128;
////                R[i][j] = 1.164 * YYY + 1.596 * VVV;
////                G[i][j] = 1.164 * YYY - 0.391 * UUU - 0.813 * VVV;
////                B[i][j] = 1.164 * YYY + 2.018 * UUU;
//                R[i][j] = 1.140 * VDecoded[i][j] + YDecoded[i][j];
//                G[i][j] = YDecoded[i][j] - 0.395*UDecoded[i][j] - 0.581*VDecoded[i][j];
//                B[i][j] = YDecoded[i][j] + 2.032*UDecoded[i][j];
//
//
//                j++;
//            }
//            i++;
//        }
//
//        // afisare in fisier
//
//        BufferedWriter writer = new BufferedWriter(new FileWriter("nt2-P3.ppm"));
//        writer.write("P3\n");
//        writer.write("# CREATOR: GIMP PNM Filter Version 1.1\n");
//        writer.write(M + " " + N + "\n");
//        writer.write(maximValue + "\n");
//
//        for(i = 1; i <= M; i++){
//            for(j = 1; j <= N; j++){
//                if(R[i][j]>255)
//                    R[i][j] = 255;
//                if(G[i][j]>255)
//                    G[i][j] = 255;
//                if(B[i][j]>255)
//                    B[i][j] = 255;
//                writer.write((int)Math.round(R[i][j]) + "\n");
//                writer.write((int)Math.round(G[i][j]) + "\n");
//                writer.write((int)Math.round(B[i][j]) + "\n");
//            }
//        }
//        writer.close();
    }
}
