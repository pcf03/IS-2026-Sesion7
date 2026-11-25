package es.uniovi.converter

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Representa el objeto "rates" del JSON: {"USD": 1.1636}
data class Rates(
    val USD: Double
)

// Representa el JSON completo:
// {"amount":1.0,"base":"EUR","date":"2025-10-29","rates":{"USD":1.1636}}
// Los nombres de las propiedades deben coincidir con las claves del JSON
data class ExchangeRateResponse(
    val amount: Double,
    val base: String,
    val date: String,
    val rates: Rates
)

// Describe la API REST: qué endpoint tiene y con qué parámetros
interface ExchangeRateApi {
    // GET https://api.frankfurter.app/latest?from=EUR&to=USD&amount=1.0
    @GET("latest")
    // suspend = función "pausable", solo se puede llamar desde una corutina
    suspend fun convert(
        @Query("from") from: String,     // moneda origen
        @Query("to") to: String,         // moneda destino
        @Query("amount") amount: Double  // cantidad a convertir
    ): Response<ExchangeRateResponse>    // Response permite ver código HTTP y cuerpo
}

// Singleton (object): una única instancia de cliente para toda la app
object RetrofitClient {
    // Dirección base; las rutas de la interfaz se añaden detrás
    private const val BASE_URL = "https://api.frankfurter.app/"

    // by lazy: se crea la primera vez que se usa, no antes
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)                                   // dónde está el servidor
            .addConverterFactory(GsonConverterFactory.create())  // cómo traducir el JSON
            .build()
    }

    // Objeto que implementa ExchangeRateApi; es el que usaremos para llamar al servicio
    val api: ExchangeRateApi by lazy {
        retrofit.create(ExchangeRateApi::class.java)
    }
}