<script setup>
import { ref } from "vue";
import api from "../services/api";

const emit = defineEmits(["done"]);

const form = ref({
  name: "",
  phoneNumber: "",
  email: "",
  address: ""
});

const error = ref("");

const submit = async () => {
  error.value = ""; // Reset error state before each attempt
  
  try {
    // We clean the payload: empty strings are sent as null 
    // to avoid backend validation/unique constraint triggers on empty values
    const payload = {
      name: form.value.name,
      phoneNumber: form.value.phoneNumber,
      email: form.value.email?.trim() || null,
      address: form.value.address?.trim() || null
    };

    await api.post("/contacts", payload);
    emit("done"); // Return to list/manage view on success
  } catch (err) {
    console.error("Submission Error:", err);

    // 1. Check if we have a structured response from the backend
    if (err.response && err.response.data) {
      const data = err.response.data;

      // Logic to handle our Custom Exception Handler or Spring's Default Error
      if (data.message) {
        // This catches the clean messages from GlobalExceptionHandler.java
        error.value = data.message;
      } else if (data.errors && data.errors.length > 0) {
        // This catches default Spring Validation array
        error.value = data.errors[0].defaultMessage;
      } else {
        error.value = "An error occurred while saving.";
      }
    } else {
      // Handles network errors (backend down, etc.)
      error.value = "Cannot connect to server. Please try again later.";
    }
  }
};
</script>

<template>
  <div class="form-container">
    <h2>Add New Contact</h2>

    <div v-if="error" class="error-banner">
      {{ error }}
    </div>

    <form @submit.prevent="submit">
      <div class="field">
        <label>Full Name *</label>
        <input 
          v-model="form.name" 
          placeholder="e.g. John Doe" 
          required 
        />
      </div>

      <div class="field">
        <label>Phone Number *</label>
        <input 
          v-model="form.phoneNumber" 
          placeholder="e.g. +1234567890" 
          required 
        />
      </div>

      <div class="field">
        <label>Email (Optional)</label>
        <input 
          v-model="form.email" 
          type="email" 
          placeholder="john@example.com"
        />
      </div>

      <div class="field">
        <label>Address (Optional)</label>
        <textarea 
          v-model="form.address" 
          rows="3" 
          placeholder="Enter physical address..."
        ></textarea>
      </div>

      <button type="submit" class="btn-submit">Save Contact</button>
    </form>
  </div>
</template>

<style scoped>
.form-container {
  max-width: 450px;
  margin: 0 auto;
}

/* Updated high-visibility heading */
h2 {
  color: #2c3e50 !important;
  margin-bottom: 20px;
  border-left: 5px solid #3498db;
  padding-left: 15px;
}

/* High-Contrast Error Message */
.error-banner {
  background-color: #fff5f5;
  color: #c0392b; /* Deep Red */
  border: 1px solid #e74c3c;
  border-left: 6px solid #c0392b;
  padding: 15px;
  margin-bottom: 20px;
  font-weight: bold;
  border-radius: 4px;
}

.field {
  margin-bottom: 15px;
  display: flex;
  flex-direction: column;
}

label {
  font-weight: 600;
  margin-bottom: 6px;
  color: #2c3e50;
}

input, textarea {
  padding: 12px;
  border: 1px solid #cbd5e0;
  border-radius: 6px;
  font-size: 1rem;
  transition: border-color 0.2s;
}

input:focus, textarea:focus {
  outline: none;
  border-color: #3498db;
  box-shadow: 0 0 0 3px rgba(52, 152, 219, 0.1);
}

.btn-submit {
  width: 100%;
  background-color: #28a745;
  color: white;
  padding: 14px;
  border: none;
  border-radius: 6px;
  font-size: 1.1rem;
  font-weight: bold;
  cursor: pointer;
  transition: background 0.2s;
  margin-top: 10px;
}

.btn-submit:hover {
  background-color: #218838;
}
</style>