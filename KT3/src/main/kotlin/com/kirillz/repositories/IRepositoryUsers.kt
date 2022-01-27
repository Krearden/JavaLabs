package com.kirillz.repositories

import com.kirillz.model.CUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface IRepositoryUsers : JpaRepository<CUser, UUID> {


}