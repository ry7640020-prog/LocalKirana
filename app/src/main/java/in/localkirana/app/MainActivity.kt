package in.localkirana.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val Green = Color(0xFF168447)

data class Product(
    val name: String,
    val price: Int
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                LocalKiranaApp()
            }
        }
    }
}

@Composable
fun LocalKiranaApp() {
    var role by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF5F8F5)
    ) {
        if (role.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "LOCAL KIRANA",
                    fontSize = 32.sp,
                    color = Green
                )

                Text(
                    "Aapki Apni Local Grocery App",
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(35.dp))

                Button(
                    onClick = { role = "customer" },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Green
                    )
                ) {
                    Text("Customer Login")
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(
                    onClick = { role = "shopkeeper" },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Shopkeeper Login")
                }
            }
        } else if (role == "customer") {
            CustomerDashboard(
                onBack = { role = "" }
            )
        } else {
            ShopkeeperDashboard(
                onBack = { role = "" }
            )
        }
    }
}

@Composable
fun CustomerDashboard(onBack: () -> Unit) {
    val products = listOf(
        Product("Aata 5 KG", 220),
        Product("Chawal 5 KG", 300),
        Product("Sugar 1 KG", 45),
        Product("Oil 1 Litre", 140),
        Product("Tea 250 Gram", 120),
        Product("Salt 1 KG", 25)
    )

    var cart by remember { mutableStateOf(0) }
    var shopCode by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("LOCAL KIRANA", fontSize = 27.sp, color = Green)

        Text("Customer Dashboard")

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = shopCode,
            onValueChange = { shopCode = it },
            label = { Text("Enter Shop Code") },
            modifier = Modifier.fillMaxWidth()
        )

        Text("SAINI GENERAL STORE DUHAR", color = Green)

        Text("Available Products", fontSize = 20.sp)

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(products) { product ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(product.name)
                            Text("₹${product.price}")
                        }

                        Button(
                            onClick = { cart++ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Green
                            )
                        ) {
                            Text("Add")
                        }
                    }
                }
            }
        }

        Text("Cart Items: $cart")

        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Green
            )
        ) {
            Text("Continue to Store Pickup")
        }

        TextButton(onClick = onBack) {
            Text("Logout")
        }
    }
}

@Composable
fun ShopkeeperDashboard(onBack: () -> Unit) {
    var productName by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var addedProducts by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "SHOPKEEPER DASHBOARD",
            fontSize = 24.sp,
            color = Green
        )

        Text("SAINI GENERAL STORE DUHAR")

        Spacer(modifier = Modifier.height(20.dp))

        Text("Add New Product", fontSize = 20.sp)

        OutlinedTextField(
            value = productName,
            onValueChange = { productName = it },
            label = { Text("Product Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = productPrice,
            onValueChange = { productPrice = it },
            label = { Text("Product Price ₹") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (productName.isNotBlank() &&
                    productPrice.toIntOrNull() != null
                ) {
                    addedProducts++
                    productName = ""
                    productPrice = ""
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Green
            )
        ) {
            Text("Add Product")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Products Added: $addedProducts")
        Text("Orders: 0")

        Text("Store Pickup Only")

        Spacer(modifier = Modifier.weight(1f))

        TextButton(onClick = onBack) {
            Text("Logout")
        }
    }
}
