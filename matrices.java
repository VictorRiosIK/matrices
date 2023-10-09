import java.io.*;
import java.net.*;
public class matrices{
            static int N = 3000; // Número de renglones
            static int M = 2000; // Número de columnas
            static int[][] matrizResultadoC=new int[N][N];
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: java MatrizMultiplicacion <numero>");
            return;
        }
        

        int opcion = Integer.parseInt(args[0]);

        switch (opcion) {
            case 1:
               
                int numThreads = 2; // Número de hilos de cliente
                Thread[] threads = new Thread[numThreads];
                 int N = 3000; // Número de renglones de la matriz
                int M = 2000; // Número de columnas de la matriz

                int[][] matrixA = new int[N][M];
                int[][] matrixB = new int[M][N];

                // Llenar la matrizA con valores usando la fórmula A[i][j] = 5*i - 2*j
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < M; j++) {
                        matrixA[i][j] = 5 * i - 2 * j;
                    }
                }
                // Llenar la matrizB con valores usando la fórmula B[i][j] = 6*i + 3*j
                for (int i = 0; i < M; i++) {
                    for (int j = 0; j < N; j++) {
                        matrixB[i][j] = 6 * i + 3 * j;
                    }
                }
                // Transponer la matrizB
                int[][] transposedMatrixB = new int[N][M];
                for (int i = 0; i < M; i++) {
                    for (int j = 0; j < N; j++) {
                        transposedMatrixB[j][i] = matrixB[i][j];
                    }
                }

                /*  System.out.println("Matriz A enviada al servidor");
                for (int i = 0; i < matrixA.length; i++) {
                    for (int j = 0; j < matrixA[i].length; j++) {
                        System.out.print(matrixA[i][j]+" ");
                    }
                    System.out.println();
                }*/
                
                /*System.out.println("Matriz B transpuesta enviada al servidor:");
                for (int i = 0; i < transposedMatrixB.length; i++) {
                    for (int j = 0; j < transposedMatrixB[i].length; j++) {
                        System.out.print(transposedMatrixB[i][j] + " ");
                    }
                    System.out.println();
                }*/
                // Definir el número de submatrices
                int numSubmatrices = 6;

                // Calcular el número de renglones por submatriz
                int renglonesPorSubmatriz = N / numSubmatrices;
                System.out.println("Renglones por sumbatriz:" + renglonesPorSubmatriz);
                
                // Crear arreglos de submatrices para los dos grupos
                int[][][] submatricesA_grupo1 = new int[numSubmatrices / 2][renglonesPorSubmatriz][M];
                int[][][] submatricesA_grupo2 = new int[numSubmatrices / 2][renglonesPorSubmatriz][M];
                
                // Dividir la matrizA en submatrices y asignarlas a los grupos
                for (int submatrizIdx = 0; submatrizIdx < numSubmatrices; submatrizIdx++) {
                    int inicioRenglon = submatrizIdx * renglonesPorSubmatriz;
                    int finRenglon = inicioRenglon + renglonesPorSubmatriz;

                    int grupoIdx = submatrizIdx < numSubmatrices / 2 ? 0 : 1; // Grupo 0 o Grupo 1
                    
                    for (int i = inicioRenglon; i < finRenglon; i++) {
                        for (int j = 0; j < M; j++) {
                            if (grupoIdx == 0) {
                                submatricesA_grupo1[submatrizIdx][i - inicioRenglon][j] = matrixA[i][j];
                            } else {
                                submatricesA_grupo2[submatrizIdx - numSubmatrices / 2][i - inicioRenglon][j] = matrixA[i][j];
                            }
                        }
                    }
                }
                /*System.out.println("IMPRIMIENDO PRIMER GRUPO");
                for (int i = 0; i < submatricesA_grupo1.length; i++) {
                    System.out.println("Grupo de submatriz " + i + ":");
                    int[][] submatriz = submatricesA_grupo1[i];
                    for (int j = 0; j < submatriz.length; j++) {
                        for (int k = 0; k < submatriz[j].length; k++) {
                            System.out.print(submatriz[j][k] + " ");
                        }
                        System.out.println();
                    }
                }*/

                
                try{ 
                   threads[0] = new MatrixClient("20.84.41.105", 50001, submatricesA_grupo1,transposedMatrixB,2);
                   threads[1] = new MatrixClient("20.84.42.77", 50001, submatricesA_grupo2,transposedMatrixB,3);
                    
                
                    for (int i = 0; i < numThreads; i++) {
                        
                        threads[i].start(); // Iniciar un nuevo hilo de cliente
                    }
                    for (int i = 0; i < numThreads; i++) {
                        
                        threads[i].join(); // Iniciar un nuevo hilo de cliente
                    }

                    Thread.sleep(10000);
                }catch(Exception e){
                    e.printStackTrace();
                }
                break;
            case 2:
                 int port = 50001; // Puerto del servidor

                    try (ServerSocket serverSocket = new ServerSocket(port)) {
                        System.out.println("Servidor esperando conexiones en el puerto " + port + "...");

                        while (true) {
                            Socket clientSocket = serverSocket.accept();
                            System.out.println("Cliente conectado desde " + clientSocket.getInetAddress());

                            // Manejamos cada cliente en un nuevo hilo
                            Thread clientThread = new ClientHandler(clientSocket);
                            clientThread.start(); // Iniciar el hilo
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                break;
            case 3:
               int port2 = 50001; // Puerto del servidor

                    try (ServerSocket serverSocket = new ServerSocket(port2)) {
                        System.out.println("Servidor esperando conexiones en el puerto " + port2 + "...");

                        while (true) {
                            Socket clientSocket = serverSocket.accept();
                            System.out.println("Cliente conectado desde " + clientSocket.getInetAddress());

                            // Manejamos cada cliente en un nuevo hilo
                            Thread clientThread = new ClientHandler(clientSocket);
                            clientThread.start(); // Iniciar el hilo
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                break;
            default:
                System.out.println("Opción no válida. Debe ser 1, 2 o 3.");
        }
       /*System.out.println("IMPRIMIENDO MATRIZ C");
       for(int i=0;i<matrizResultadoC.length;i++){
            for(int j=0;j<matrizResultadoC[0].length;j++){
                System.out.print(matrizResultadoC[i][j]+" ");
            }
            System.out.println();
       }*/
       // Inicializar una variable para almacenar la suma
        int suma = 0;

        // Recorrer la matriz y sumar todos los elementos
        for (int i = 0; i < matrizResultadoC.length; i++) {
            for (int j = 0; j < matrizResultadoC[0].length; j++) {
                suma += matrizResultadoC[i][j];
            }
        }
        System.out.println("CHECKSUM: " + suma);
    }
    static class ClientHandler extends Thread {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {

            // Recibimos los grupos de submatrices A del cliente
                int[][][] submatricesA_grupo = (int[][][]) in.readObject();

                // Recibimos la matriz B transpuesta del cliente (una sola vez)
                int[][] transposedMatrixB = (int[][]) in.readObject();
                int numComputadora=(int)in.readObject();

                // Procesamos las submatrices A (puedes iterar sobre submatricesA_grupo)
                for (int i = 0; i < submatricesA_grupo.length; i++) {
                    System.out.println("Grupo de submatriz " + i + ":");
                    int[][] submatriz = submatricesA_grupo[i];
                    /*for (int j = 0; j < submatriz.length; j++) {
                        for (int k = 0; k < submatriz[j].length; k++) {
                            System.out.print(submatriz[j][k] + " ");
                        }
                        System.out.println();
                    }*/
                }
                // Mostramos la matriz B transpuesta en el servidor
                /*System.out.println("Matriz B transpuesta recibida del cliente:");
                for (int i = 0; i < transposedMatrixB.length; i++) {
                    for (int j = 0; j < transposedMatrixB[i].length; j++) {
                        System.out.print(transposedMatrixB[i][j] + " ");
                    }
                    System.out.println();
                }*/
                // Crear la matriz resultado
                int[][] resultado = new int[submatricesA_grupo.length][transposedMatrixB.length];
                
                
                // Realizar la multiplicación de las submatrices por transposedMatrixB
                for (int i = 0; i < submatricesA_grupo.length; i++) {
                    System.out.println();
                    int[][] submatriz = submatricesA_grupo[i];
                    for (int j = 0; j < submatriz.length; j++) {//aqui itera 3 veces

                        for (int k = 0; k <= submatriz[j].length; k++) {//aqui itera 6 veces
                        int suma=0;
                            for(int l=0;l<submatriz[j].length;l++){//itera 6 veces
                                suma+=(submatriz[j][l]*transposedMatrixB[k][l]);
                                
                                //System.out.print((submatriz[j][l]*transposedMatrixB[k][l]) + " ");
                            }
                            //System.out.print(suma + " ");
                            
                            resultado[i][k]=suma;
                        }
                       
                    }
                       
                    
                }
                
                
                out.writeObject(resultado);
                out.writeObject(numComputadora);
            // Enviamos una respuesta al cliente
            out.writeUTF("Matriz recibida con éxito en el servidor.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    }
    public static class MatrixClient extends Thread {
    private String serverAddress;
    private int serverPort;
    private int[][][] matrixA;
    private int[][] transposedMatrixB;
    private int numComputadora;

    public MatrixClient(String serverAddress, int serverPort, int[][][] matrixA,int[][] transposedMatrixB, int numComputadora) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.matrixA = matrixA;
        this.transposedMatrixB = transposedMatrixB;
        this.numComputadora=numComputadora;
    }

    @Override
    public void run() {
        try (Socket socket = new Socket(serverAddress, serverPort);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            
             
            // Enviar la matriz al servidor
            out.writeObject(matrixA);
            // Enviar la matriz B transpuesta al servidor
            out.writeObject(transposedMatrixB);
            out.writeObject(numComputadora);

            // Recibir la matriz resultado del servidor
            try{
                int[][] resultMatrix = (int[][]) in.readObject();
                /*System.out.println("\n\n\nMatriz resultado recibida desde el servidor para su construccion:");
                for (int i = 0; i < resultMatrix.length; i++) {
                    for (int j = 0; j < resultMatrix[i].length; j++) {
                        System.out.print(resultMatrix[i][j] + " ");
                    }
                    System.out.println();
                }*/
                int numComputadora=(int)in.readObject();
                System.out.println("NUMERO DE COMPUTADORA:" + numComputadora);
                if(numComputadora==2){
                    // Copiar la primera submatriz en la matriz principal (renglones 0 a 2)
                    for (int i = 0; i < resultMatrix.length; i++) {
                        for (int j = 0; j < resultMatrix[0].length; j++) {
                            matrizResultadoC[i][j] = resultMatrix[i][j];
                    }
                    }
                }else if(numComputadora==3){
                    // Copiar la segunda submatriz en la matriz principal (renglones 3 a 5)
                    for (int i = 0; i < resultMatrix.length; i++) {
                        for (int j = 0; j < resultMatrix[0].length; j++) {
                            matrizResultadoC[i + resultMatrix.length][j] = resultMatrix[i][j];
                        }
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
                
            // Recibir respuesta del servidor
            String response = in.readUTF();
            System.out.println("Respuesta del servidor: " + response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }
}

