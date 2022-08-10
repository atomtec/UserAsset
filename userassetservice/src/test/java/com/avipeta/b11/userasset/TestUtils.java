package com.avipeta.b11.userasset;

import com.avipeta.b11.userasset.remote.dto.TransferDTO;

public class TestUtils {

	public static TransferDTO getTransferfromInventoryDTO() {
		TransferDTO transfer = new TransferDTO();
		transfer.setAdminid(5L);
		transfer.setFromuserid(0L);
		transfer.setTouserid(1L);
		transfer.setAssetid(1L);
		return transfer;
	}
	
	
	public static TransferDTO getTransfertoInventoryDTO() {
		TransferDTO transfer = new TransferDTO();
		transfer.setAdminid(5L);
		transfer.setFromuserid(2L);
		transfer.setTouserid(0L);
		transfer.setAssetid(2L);
		return transfer;
	}

}
