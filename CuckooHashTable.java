

import java.util.Scanner;

public class CuckooHashTable {
    private final int MAX_TABLES = 5;
    private final int MIN_SIZE = 10;
    private final int MAX_SIZE = 30;
    private int numTables;
    private int[] tableSizes;
    private int[][] tables;
    private int collisions;
    private int items;

    public CuckooHashTable() {
    	
        Scanner in = new Scanner(System.in);

        System.out.print("Enter the number of tables (up to " + MAX_TABLES + "): ");
        numTables = in.nextInt();
        
        if(numTables > MAX_TABLES) {
        	System.out.println("Number of tables too much. " + MAX_TABLES + " tables created.");
        	numTables = MAX_TABLES;
        }

        tableSizes = new int[numTables];
        tables = new int[numTables][];

        for (int i = 0; i < numTables; i++) {
        	
            System.out.print("Enter the size of table " + (i + 1) + " (between " + MIN_SIZE + " and " + MAX_SIZE + "): ");
            tableSizes[i] = in.nextInt();
            
            if(tableSizes[i] < MIN_SIZE) {
            	System.out.println("Table size too small. It created as " + MIN_SIZE);
            	tableSizes[i] = MIN_SIZE;
            }
            else if (tableSizes[i] > MAX_SIZE) {
            	System.out.println("Table size too large. It created as " + MAX_SIZE);
            	tableSizes[i] = MAX_SIZE;
            }
            
            tables[i] = new int[tableSizes[i]];
        }

        while (true) {
        	
            System.out.println("\nMenu:");
            System.out.println("1. Insert key");
            System.out.println("2. Search for key");
            System.out.println("3. Delete key");
            System.out.println("4. Print tables");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = in.nextInt();

            switch (choice) {
                case 1:
                    insertKey();
                    break;
                case 2:
                    searchKey();
                    break;
                case 3:
                    deleteKey();
                    break;
                case 4:
                	visualization();
                	break;
                case 5:
                    System.out.println("Exiting program...");
                    System.exit(0);
            }
        }
    }

    private int hashFunction(int key) {
        return key % tableSizes[0];
    }

    private void insertKey() {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the key to be inserted: ");
        int key = in.nextInt();
        
        System.out.println("\nTables before inserting operation: \n");
        visualization();

        int hash = hashFunction(key);
        int tableNum = 0;
        int currentKey = key;
        int currentTable = 0;
        while (true) {
            if (tables[currentTable][hash] == 0) {
                tables[currentTable][hash] = currentKey;
                tableNum = currentTable;
                items++;
                break;
            } else {
                collisions++;
                int temp = tables[currentTable][hash];
                tables[currentTable][hash] = currentKey;
                currentKey = temp;
                currentTable = (currentTable + 1) % numTables;
                hash = hashFunction(currentKey);
            }
        }
        
        System.out.println("Key " + key + " inserted in table " + (tableNum + 1) + " at index " + hash);
        
        System.out.println("Collisions: " + collisions);
        System.out.println("Load factor: " + (float)(items*100)/tableSizes[tableNum] +"%");
        
        System.out.println("\nTables after inserting operation: \n");
        visualization();
    }

    private void searchKey() {
    	
        Scanner in = new Scanner(System.in);

        System.out.print("Enter the key to be searched: ");
        int key = in.nextInt();

        int hash = hashFunction(key);
        int tableNum = -1;
        for (int i = 0; i < numTables; i++) {
            if (tables[i][hash] == key) {
                tableNum = i;
                break;
            }
        }

        if (tableNum == -1) {
            System.out.println("Key not found!");
        } else {
            System.out.println("Key " + key + " found in table " + (tableNum + 1) + " at index " + hash);
        }
    }

    private void deleteKey() {
    	
        Scanner in = new Scanner(System.in);

        System.out.print("Enter the key to be deleted: ");
        int key = in.nextInt();
        
        System.out.println("\nTables before deleting operation: \n");
        visualization();

        int hash = hashFunction(key);
        int tableNum = -1;
        for (int i = 0; i < numTables; i++) {
            if (tables[i][hash] == key) {
                tables[i][hash] = 0;
                tableNum = i;
                break;
            }
        }

        if (tableNum == -1) {
            System.out.println("Key not found!");
        } else {
            System.out.println("Key " + key + " deleted from table " + (tableNum + 1) + " at index " + hash);
        }
        
        System.out.println("\nTables after deleting operation: \n");
        visualization();
        System.out.println("");
    }
    
    private void visualization() {
    	for (int i = 0; i < numTables; i++) {
            System.out.println("Table " + (i + 1) + ":");
            for (int j = 0; j < tableSizes[i]; j++) {
                System.out.print(tables[i][j] + " ");
            }
            System.out.println("");
        }
    }

    public static void main(String[] args) {
        new CuckooHashTable();
    }
}

