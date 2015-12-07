package com.imp.saas.repository;

import org.springframework.data.repository.CrudRepository;

import com.imp.saas.domain.Provider;

/**
 * Interface for CRUD operations
 * @author rakesh.singhania
 *
 */
public interface ProviderRepository extends CrudRepository<Provider, Long>{

}
