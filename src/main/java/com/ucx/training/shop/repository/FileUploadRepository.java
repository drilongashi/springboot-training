package com.ucx.training.shop.repository;

import com.ucx.training.shop.entity.FileUpload;
import com.ucx.training.shop.entity.Product;
import com.ucx.training.shop.type.RecordStatus;

public interface FileUploadRepository extends BaseRepository<FileUpload, Integer> {
    FileUpload findByProductAndRecordStatus(Product product, RecordStatus recordStatus);

    FileUpload findByProduct(Product product);
}