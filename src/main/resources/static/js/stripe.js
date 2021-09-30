
// A reference to Stripe.js initialized with your real test publishable API key.
const publicKey = document.getElementById('stripe-public-key').value;
var stripe = Stripe(publicKey);

var orderData = {
    currency: "USD"
};

const createPaymentIntentUrl = "/create-donation-intent";
const saveDonationUrl = "/save-donation-success";
const donationSuccessPage = "/donation-success-page";

document.querySelector("button").disabled = false;
const elements = stripe.elements();
const style = {
    base: {
        color: "#32325d",
        fontFamily: '"Helvetica Neue", Helvetica, sans-serif',
        fontSmoothing: "antialiased",
        fontSize: "16px",
        "::placeholder": {
            color: "#aab7c4"
        }
    },
    invalid: {
        color: "#fa755a",
        iconColor: "#fa755a"
    }
};

const card = elements.create("card", { style: style , hidePostalCode : true});
card.mount("#stripe-place");

const form = document.getElementById("donation-form");
form.addEventListener("submit", function(event) {
    event.preventDefault();
    pay(stripe, card);
});

const handleAction = function(clientSecret) {
    stripe.handleCardAction(clientSecret).then(function(data) {
        if (data.error) {
            showError("Your card was not authenticated, please try again");
        } else if (data.paymentIntent.status === "requires_confirmation") {
            fetch(createPaymentIntentUrl, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    paymentIntentId: data.paymentIntent.id,
                    addressLine: document.getElementById('addressLine').value,
                    town: document.getElementById('city').value,
                    country: document.getElementById('country').value,
                    name: document.getElementById('nameOnCard').value,
                    email: document.getElementById('emailAddress').value
                })
            })
                .then(function(result) {
                    return result.json();
                })
                .then(function(json) {
                    console.log("require action response : ", json);
                    if (json.error) {
                        showError(json.message);
                    } else {
                        //orderComplete(clientSecret);
                        window.open(paymentSuccessUrl,"_self");
                    }
                });
        }
    });
};

/*
 * Collect card details and pay for the order
 */
const pay = function(stripe, card) {
    changeLoadingState(true);
    // Collects card details and creates a PaymentMethod
    const cardHolderName = document.getElementById('nameOnCard').value;

    orderData.addressLine = document.getElementById('addressLine').value;
    orderData.town = document.getElementById('city').value;
    orderData.country = document.getElementById('country').value;
    orderData.name = cardHolderName;
    orderData.email = document.getElementById('emailAddress').value;
    orderData.amount = document.getElementById('amount').value;

    stripe.createPaymentMethod(
            {
                type: 'card',
                card : card,
                billing_details: {
                    address: {
                        city: orderData.town,
                        //country: orderData.country,
                        line1: orderData.addressLine
                    },
                    name: cardHolderName,
                }
            }
        )
        .then(function(result) {
            if (result.error) {
                showError(result.error.message);
            } else {
                orderData.paymentMethodId = result.paymentMethod.id;

                return fetch(createPaymentIntentUrl, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(
                        orderData
                    )
                });
            }
        })
        .then(function(result) {
            return result.json();
        })
        .then(function(response) {
            console.log("response stripe-module : ", response);
            if (response.error) {
                showError(response.message);
            }
            else if (response.requireAction) {
                // Request authentication
                handleAction(response.clientSecret);
            }
            else {
                window.open(donationSuccessPage,"_self");
            }
        });
};



// Show the customer the error from Stripe if their card fails to charge
const showError = function(errorMsgText) {
    changeLoadingState(false);
    var errorMsg = document.querySelector("#card-error");
    errorMsg.textContent = errorMsgText;
    setTimeout(function() {
        errorMsg.textContent = "";
    }, 12000);
};

// Show a spinner on payment submission
const changeLoadingState = function(isLoading) {
    if (isLoading) {
        document.querySelector("button").disabled = true;
        document.querySelector("#spinner").classList.remove("hidden");
        document.querySelector("#button-text").classList.add("hidden");
    } else {
        document.querySelector("button").disabled = false;
        document.querySelector("#spinner").classList.add("hidden");
        document.querySelector("#button-text").classList.remove("hidden");
    }
};
