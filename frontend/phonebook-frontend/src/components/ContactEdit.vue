<script setup>
import { ref, onMounted } from "vue";
import api from "../services/api";

const props = defineProps({
  id: {
    type: Number,
    required: true
  }
});

const emit = defineEmits(["done"]);

const form = ref({
  name: "",
  phoneNumber: "",
  email: "",
  address: ""
});

const loading = ref(true);
const error = ref("");

// Fetch the existing details so the user can see them
const fetchContactDetails = async () => {
  try {
    loading.value = true;
    const res = await api.get(`/contacts/${props.id}`);
    form.value = res.data;
  } catch (err) {
    error.value = "Could not load contact details.";
    console.error(err);
  } finally {
    loading.value = false;
  }
};

const updateContact = async () => {
  try {
    await api.put(`/contacts/${props.id}`, form.value);
    alert("Update successful!");
    emit("done"); // Navigate back to the list/manage view
  } catch (err) {
    error.value = err.response?.data || "Update failed. Check your data.";
    console.error(err);
  }
};

onMounted(fetchContactDetails);
</script>

<template>
  <div class="edit-container">
    <h2>Edit Contact Details</h2>

    <p v-if="loading">Loading current details...</p>
    <p v-if="error" class="error">{{ error }}</p>

    <form v-if="!loading" @submit.prevent="updateContact">
      <div class="form-group">
        <label>Name:</label>
        <input v-model="form.name" required />
      </div>

      <div class="form-group">
        <label>Phone:</label>
        <input v-model="form.phoneNumber" required />
      </div>

      <div class="form-group">
        <label>Email:</label>
        <input v-model="form.email" type="email" />
      </div>

      <div class="form-group">
        <label>Address:</label>
        <textarea v-model="form.address" rows="3"></textarea>
      </div>

      <div class="actions">
        <button type="submit" class="btn-save">Save Changes</button>
        <button type="button" class="btn-cancel" @click="emit('done')">Cancel</button>
      </div>
    </form>
  </div>
</template>

<style scoped>
.edit-container { max-width: 400px; margin: auto; }
.form-group { margin-bottom: 15px; display: flex; flex-direction: column; }
label { font-weight: bold; margin-bottom: 5px; }
input, textarea { padding: 10px; border: 1px solid #ccc; border-radius: 4px; }
.actions { display: flex; gap: 10px; margin-top: 20px; }
.btn-save { background: #28a745; color: white; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer; flex: 1; }
.btn-cancel { background: #6c757d; color: white; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer; flex: 1; }
.error { color: red; background: #f8d7da; padding: 10px; border-radius: 4px; }
</style>