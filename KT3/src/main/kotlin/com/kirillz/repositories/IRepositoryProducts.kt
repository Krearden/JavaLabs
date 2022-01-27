package com.kirillz.repositories

import com.kirillz.model.CProduct
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface IRepositoryProducts : JpaRepository<CProduct, UUID> {

}