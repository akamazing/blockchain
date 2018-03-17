package noobChain;

import java.util.ArrayList;
import java.util.Date;

import com.google.gson.GsonBuilder;

public class Block {
	public String Hash;
	public String PrevHash;
	private String Data;
	private long timeStamp;
	
	public Block(String data, String prevHash){
		this.Data = data;
		this.PrevHash = prevHash;
		this.timeStamp = new Date().getTime();
		this.Hash = calculateHash();
	}
	
	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	
	public String calculateHash() {
		String calculatedhash = StringUtil.encryptSHA256( 
				PrevHash +
				Long.toString(timeStamp) +
				Data 
				);
		return calculatedhash;
	}
	
	public static Boolean isChainValid() {
		Block currentBlock; 
		Block previousBlock;
		
		//loop through blockchain to check hashes:
		for(int i=1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i-1);
			//compare registered hash and calculated hash:
			if(!currentBlock.Hash.equals(currentBlock.calculateHash()) ){
				System.out.println("Current Hashes not equal");			
				return false;
			}
			//compare previous hash and registered previous hash
			if(!previousBlock.Hash.equals(currentBlock.PrevHash) ) {
				System.out.println("Previous Hashes not equal");
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		
		blockchain.add(new Block("Hi im the first block", "0"));		
		blockchain.add(new Block("Yo im the second block",blockchain.get(blockchain.size()-1).Hash)); 
		blockchain.add(new Block("Hey im the third block",blockchain.get(blockchain.size()-1).Hash));
		
		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);		
		System.out.println(blockchainJson);
		
		if (isChainValid()){
			System.out.println("Chain Valid so far!!!");
		}
		else{
			System.out.println("lol!!!");
		}
	}

}
