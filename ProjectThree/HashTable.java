package ProjectThree;


public class HashTable {
    private int tableSize;
    private Vertix[] table;

    HashTable(int tableSize) {
        this.tableSize = tableSize;
        this.table=new Vertix[tableSize];
        for (int i = 0; i < tableSize; i++) {
            table[i] = null; 
        }
    }

    public void put(Vertix value) {
        int hash = getHash(value.getCountry().getCountryName());
        while (table[hash] != null && !table[hash].getCountry().getCountryName().equals(value.getCountry().getCountryName())) {
            hash = (hash + 1) % tableSize;
        }
        table[hash] = value;
    }

    public Vertix getVertex(String key) {
        int hash = getHash(key);
        while (table[hash] != null && !table[hash].getCountry().getCountryName().equals(key)) {
            hash = (hash + 1) % tableSize;
        }
        if (table[hash] == null) {
            return null;
        } else {
            return table[hash];
        }
    }
    
    public int getVertexIndex(String key) {
        int hash = getHash(key);
        while (table[hash] != null && !table[hash].getCountry().getCountryName().equals(key)) {
            hash = (hash + 1) % tableSize;     
        }
        if (table[hash] == null) {
            return -1;
        } else {
            return hash;
        }
    }

    private int getHash(String key) {
        int hash = key.hashCode() % tableSize;
        if (hash < 0) {
            hash += tableSize;
        }
        return hash;
    }

	public int getTableSize() {
		return tableSize;
	}

	public Vertix[] getTable() {
		return table;
	}
	
    public void setAllVerticesToFalse() {
        for (Vertix vertix : table)
            if (vertix != null)
                vertix.setVisited(false);
    }
}
