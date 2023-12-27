import java.io.File;
import java.util.Scanner;

public class Greedy {

    public static void main(String[] args) {
        // Read input path from command line
        String filepath = args[0];
        for (int i = 1; i < args.length; i++)
            filepath += " " + args[i];
        File input = new File(filepath);

        // ***INSTRUCTIONS FOR RUNNING TASKS***
        // To run tasks B or C, pass the path to a correctly formatted text file as a command line argument,
        // and uncomment to call to TASK_B or TASK_C below.
        // To run task D, pass the path to a directory containing one or more correctly formatted text files
        // as a command line argument, and uncomment to call to TASK_D below.

        // Algorithm 1: Greedy Bin Packing
        //TASK_B(input);

        // Algorithm 2: Greedy Decreasing Bin Packing
        //TASK_C(input);

        // Experimental evaluation of algorithms 1 and 2
        TASK_D(input);
    }

    private static void TASK_B(File input) {
        // Read the sizes of input folders and store the result in an integer array
        int[] folderSizes = readFolderSizes(input);
        // Run the greedy Bin Packing algorithm on the read folder sizes and store the result in a priority queue of Disks
        MaxPQ<Disk> resultAllocation = greedyBinPacking(folderSizes);
        // Display the results
        printTasksBCResults(folderSizes, resultAllocation);
    }

    private static void TASK_C(File input) {
        // Read the sizes of input folders and store the result in an integer array
        int[] folderSizes = readFolderSizes(input);
        // Sort the read folder sizes in decreasing order
        Sort.mergeSort(folderSizes);
        // Run the greedy Bin Packing algorithm on the read folder sizes and store the result in a priority queue of Disks
        MaxPQ<Disk> resultAllocation = greedyBinPacking(folderSizes);
        // Display the results
        printTasksBCResults(folderSizes, resultAllocation);
    }

    private static int[] readFolderSizes(File input) {
        // Count the number of lines of the input file
        // If you come across invalid paths or empty files, display a message and exit
        int linesNum = 0;
        try {
            Scanner scan = new Scanner(input);
            if (!scan.hasNextLine()) {
                System.out.println("The specified file is empty. Please pass in a correctly formatted text file.");
                System.exit(-1);
            }
            while (scan.hasNextLine()) {
                linesNum++;
                scan.nextLine();
            }
            scan.close();
        }
        catch(Exception FileNotFoundException) {
            System.out.println("The path passed in as a command line argument does not correspond to a file.");
            System.exit(-1);
        }
        // Declare an integer array
        int[] folderSizes = new int[linesNum];
        // Fill it each folder size in the specified file
        // If you come across non-integer values and integers out of bounds, display a message and exit
        try {
            Scanner scan = new Scanner(input);
            for (int i = 0; i < linesNum; i++) {
                int folderSize = Integer.parseInt(scan.nextLine());
                if (folderSize >= 0 && folderSize <= 1_000_000)
                    folderSizes[i] = folderSize;
                else {
                    System.out.println("At least one line in the given file contains an integer below 0 or above 1,000,000. Please keep the folder sizes within these bounds.");
                    System.exit(-1);
                }
            }
            scan.close();
        }
        catch(Exception NoSuchElementException) {
            System.out.println("At least one line in the given file is not an integer. Please include only integer folder sizes.");
            System.exit(-1);
        }
        // Return the filled array
        return folderSizes;
    }

    private static MaxPQ<Disk> greedyBinPacking(int[] folderSizes) {
        // Declare a priority queue of disks
        MaxPQ<Disk> diskPQ = new MaxPQ<>();
        // Initialize the first disk with the first folder from the input,
        // and insert the disk into the priority queue
        Disk firstDisk = new Disk();
        firstDisk.insert(folderSizes[0]);
        diskPQ.insert(firstDisk);
        // go through the rest of the folders from the input
        for (int i = 1; i < folderSizes.length; i++) {
            // if the current folder size is larger than the disk with the largest amount of free space
            // in the priority queue, initialize a new disk with the current folder,
            // and insert that disk into the priority queue
            if (diskPQ.peekmax().getFreeSpace() < folderSizes[i]) {
                Disk newDisk = new Disk();
                newDisk.insert(folderSizes[i]);
                diskPQ.insert(newDisk);
            }
            // otherwise, remove the disk with the largest amount of free space from the priority queue,
            // add the current folder into it, and insert the disk back into the priority queue
            else {
                Disk maxDisk = diskPQ.getmax();
                maxDisk.insert(folderSizes[i]);
                diskPQ.insert(maxDisk);
            }
        }
        // Return the priority queue with the allocation of folder sizes to disks
        return diskPQ;
    }

    private static void printTasksBCResults(int[] folderSizes, MaxPQ<Disk> resultAllocation) {
        // Compute the total folder size of the input file in terabytes
        double folderSizeSumTB = 0.0;
        for (int folderSize : folderSizes)
            folderSizeSumTB += folderSize;
        folderSizeSumTB = folderSizeSumTB/1_000_000;
        // Print out allocation information
        System.out.println("Sum of all folders = " + folderSizeSumTB + " TB");
        System.out.println("Total number of disks used = " + resultAllocation.getPQSize());
        // If at most 100 disks were allocated into disks, display each disk,
        // and the size of the folder it contains, in order of decreasing free space
        if (folderSizes.length <= 100) {
            int diskNum = resultAllocation.getPQSize();
            for (int i = 0; i < diskNum; i++) {
                Disk currDisk = resultAllocation.getmax();
                System.out.print("id " + currDisk.getID() + " " + currDisk.getFreeSpace() + ":");
                int currDiskSize = currDisk.getDiskSize();
                for (int j = 0; j < currDiskSize; j++)
                    System.out.print(" " + currDisk.getFolders().removeFromFront());
                System.out.println();
            }
        }
        // otherwise, display a message
        else
            System.out.println("More than a hundred folder sizes have been specified, therefore no specifics about the folder to disk allocation will be displayed.");
    }

    private static void TASK_D(File input) {
        // List the files in the specified directory and store them in a File array
        File[] fileArray = input.listFiles();
        // If the input does not correspond to a directory, display a message and exit
        if (fileArray == null) {
            System.out.println("The path given as command line argument does not correspond to a directory. Please pass in the path to a directory of correctly formatted text files.");
            System.exit(-1);
        }
        // If the directory specified is empty, display a message and exit
        if (fileArray.length == 0) {
            System.out.println("The directory specified is empty. Please pass in the path to a directory of one or more correctly formatted text files.");
            System.exit(-1);
        }
        // Declare an integer for the length of the input
        int dataLength = fileArray.length;
        // Declare an array of folder size arrays
        int[][] folderSizeArray = new int[dataLength][];
        // Fill each subarray with the sizes of input folders for each file in the specified directory
        for (int i = 0; i < dataLength; i++) {
            folderSizeArray[i] = readFolderSizes(fileArray[i]);
        }
        // Declare an array of max priority queues of disks
        MaxPQ<Disk>[] greedyAllocationArray = new MaxPQ[dataLength];
        // Run the greedy Bin Packing algorithm for each file in the specified directory
        // and store each result in the priority queue array
        for (int i = 0; i < dataLength; i++) {
            greedyAllocationArray[i] = greedyBinPacking(folderSizeArray[i]);
        }
        // Declare another array of max priority queues of disks
        MaxPQ<Disk>[] greedyDecrAllocationArray = new MaxPQ[dataLength];
        // Sort the read folder sizes in decreasing order for each file in the specified directory
        // Run the greedy Bin Packing algorithm for each file in the specified directory
        // and store each result in the priority queue array
        for (int i = 0; i < dataLength; i++) {
            Sort.mergeSort(folderSizeArray[i]);
            greedyDecrAllocationArray[i] = greedyBinPacking(folderSizeArray[i]);
        }
        // Declare counters for the sum of disks used for all files in the specified directory
        // that belong to the same category of amount of folder sizes included (100, 500, or 1000),
        // and for each of the two algorithms used
        int greedySum100 = 0, greedyDecrSum100 = 0;
        int greedySum500 = 0, greedyDecrSum500 = 0;
        int greedySum1000 = 0, greedyDecrSum1000 = 0;
        // Compute the length of each category (assumption: there are three categories with equal amount of corresponding files)
        int categoryLength = dataLength / 3;
        // Go through all files in the specified directory
        for (int i = 0; i < dataLength; i++) {
            // Get the file name of the current file
            String fileName = fileArray[i].getAbsolutePath();
            // If the file belongs to the 100 category, update the corresponding counters
            if (fileName.contains("N = 100 ")) {
                greedySum100 += greedyAllocationArray[i].getPQSize();
                greedyDecrSum100 += greedyDecrAllocationArray[i].getPQSize();
            }
            // If the file belongs to the 500 category, update the corresponding counters
            else if (fileName.contains("N = 500 ")) {
                greedySum500 += greedyAllocationArray[i].getPQSize();
                greedyDecrSum500 += greedyDecrAllocationArray[i].getPQSize();
            }
            // If the file belongs to the 1000 category, update the corresponding counters
            else if (fileName.contains("N = 1000 ")) {
                greedySum1000 += greedyAllocationArray[i].getPQSize();
                greedyDecrSum1000 += greedyDecrAllocationArray[i].getPQSize();
            }
        }
        // Compute the mean number of disks used for each category and for each algorithm
        float greedyMean100 = (float)greedySum100/categoryLength, greedyDecrMean100 = (float)greedyDecrSum100/categoryLength;
        float greedyMean500 = (float)greedySum500/categoryLength, greedyDecrMean500 = (float)greedyDecrSum500/categoryLength;
        float greedyMean1000 = (float)greedySum1000/categoryLength, greedyDecrMean1000 = (float)greedyDecrSum1000/categoryLength;

        // Display the means for each category and for each algorithm
        System.out.println("N = 100: Greedy = " + greedyMean100 + ", Greedy-Decreasing = " + greedyDecrMean100);
        System.out.println("N = 500: Greedy = " + greedyMean500 + ", Greedy-Decreasing = " + greedyDecrMean500);
        System.out.println("N = 1000: Greedy = " + greedyMean1000 + ", Greedy-Decreasing = " + greedyDecrMean1000);
    }

}
