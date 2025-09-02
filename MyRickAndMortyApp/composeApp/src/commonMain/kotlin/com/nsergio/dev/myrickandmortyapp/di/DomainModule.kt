package com.nsergio.dev.myrickandmortyapp.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

// module
/**
 * in this context means, that koin will provide a new instance every time
 * ```
 * factory<NameOperator> { NameOperator() }
 * ```

 * in this context means, that the same instance will be provided
 * and `get()` means that koin will provide a NameOperator instance
 *
 * ```
 * //single<GetName> { GetName(nameOperator = get()) }
 * factoryOf(::GetName)
 * ```
 * class
 * ```
 * class NameOperator
 * ```
 * ```
 * class GetName(nameOperator: NameOperator)
 * ```
 */
val domainModule = module {

}