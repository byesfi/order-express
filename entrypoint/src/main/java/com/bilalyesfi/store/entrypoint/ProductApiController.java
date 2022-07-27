package com.bilalyesfi.store.entrypoint;

import com.bilalyesfi.store.domain.model.Product;
import com.bilalyesfi.store.domain.usecase.product.CreateProductCommand;
import com.bilalyesfi.store.domain.usecase.product.DeleteProductCommand;
import com.bilalyesfi.store.domain.usecase.product.UpdateProductCommand;
import com.bilalyesfi.store.entrypoint.dto.ProductRequest;
import com.bilalyesfi.store.entrypoint.util.ControllerHelper;
import com.bilalyesfi.store.entrypoint.util.ProductMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/product")
public class ProductApiController {

  private final CreateProductCommand createProductCommand;

  private final UpdateProductCommand updateProductCommand;

  private final DeleteProductCommand deleteProductCommand;

  public ProductApiController(CreateProductCommand createProductCommand,
      UpdateProductCommand updateProductCommand, DeleteProductCommand deleteProductCommand) {
    this.createProductCommand = createProductCommand;
    this.updateProductCommand = updateProductCommand;
    this.deleteProductCommand = deleteProductCommand;
  }

  @PostMapping("/create")
  public ResponseEntity<?> createProduct(@RequestBody @Valid ProductRequest productRequest,
                                         BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ControllerHelper.getBindingErrorResponse(bindingResult);
    }

    Product createdProduct = createProductCommand.handle(ProductMapper.mapToDomain(productRequest));
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(createdProduct);
  }

  @PutMapping
  public ResponseEntity<?> updateProduct(@RequestBody @Valid ProductRequest productRequest,
                                         BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ControllerHelper.getBindingErrorResponse(bindingResult);
    }

    Product updatedProduct = updateProductCommand.handle(ProductMapper.mapToDomain(productRequest));
      return ResponseEntity
              .status(HttpStatus.OK)
              .body(updatedProduct);
  }

  @DeleteMapping("{productId}")
  public void deleteProduct(@PathVariable Long productId) {
    deleteProductCommand.handle(productId);
  }
}
