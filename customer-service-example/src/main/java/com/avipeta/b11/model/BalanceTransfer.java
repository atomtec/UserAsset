package com.avipeta.b11.model;

public class BalanceTransfer {
	
	private Long cutsomerid;
	
    private Long srcAccountid;
	
	private Long destAccountid;
	
	double transferAmount = 0.0;
	
	public Long getCutsomerid() {
		return cutsomerid;
	}

	public void setCutsomerid(Long cutsomerid) {
		this.cutsomerid = cutsomerid;
	}

	public Long getSrcAccountid() {
		return srcAccountid;
	}

	public void setSrcAccountid(Long srcAccountid) {
		this.srcAccountid = srcAccountid;
	}

	public Long getDestAccountid() {
		return destAccountid;
	}

	public void setDestAccountid(Long destAccountid) {
		this.destAccountid = destAccountid;
	}

	public double getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(double transferAmount) {
		this.transferAmount = transferAmount;
	}

	
	
	

}
