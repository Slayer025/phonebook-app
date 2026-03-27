<script setup>
import { ref, onMounted, computed } from "vue";
import api from "../services/api";

const contacts = ref([]);
const loading = ref(true);
const error = ref("");
const searchQuery = ref(""); // Search state

const emit = defineEmits(["edit"]);

const fetchContacts = async () => {
  loading.value = true;
  try {
    const res = await api.get("/contacts");
    contacts.value = res.data;
  } catch (err) {
    error.value = "Failed to load contacts. Is the backend running?";
    console.error("Fetch Error:", err);
  } finally {
    loading.value = false;
  }
};

// Logic to filter contacts based on search query
const filteredContacts = computed(() => {
  const query = searchQuery.value.toLowerCase().trim();
  if (!query) return contacts.value;

  return contacts.value.filter(c => {
    return (
      c.name.toLowerCase().includes(query) || 
      c.phoneNumber.includes(query) ||
      (c.email && c.email.toLowerCase().includes(query))
    );
  });
});

const deleteContact = async (id) => {
  if (confirm("Are you sure you want to delete this contact?")) {
    try {
      await api.delete(`/contacts/${id}`);
      contacts.value = contacts.value.filter(c => c.id !== id);
    } catch (err) {
      console.error("Delete Error:", err.response || err);
      alert("Delete failed.");
    }
  }
};

const triggerEdit = (id) => {
  emit("edit", id);
};

onMounted(fetchContacts);
</script>

<template>
  <div class="management-container">
    <div class="header-row">
      <div class="title-group">
        <h2>Manage Contacts</h2>
        <p v-if="contacts.length" class="count-badge">Total: {{ contacts.length }}</p>
      </div>
      
      <div class="actions-group">
        <input 
          v-model="searchQuery" 
          type="text" 
          placeholder="Filter contacts..." 
          class="search-bar"
        />
        <button class="btn-refresh" @click="fetchContacts">Refresh</button>
      </div>
    </div>

    <p v-if="loading" class="status">Loading contacts...</p>
    <p v-if="error" class="error-msg">{{ error }}</p>

    <table v-if="!loading && filteredContacts.length" class="styled-table">
      <thead>
        <tr>
          <th>Name</th>
          <th>Phone Number</th>
          <th>Email</th>
          <th>Address</th>
          <th class="actions-cell">Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="c in filteredContacts" :key="c.id">
          <td>{{ c.name }}</td>
          <td>{{ c.phoneNumber }}</td>
          <td>{{ c.email || '-' }}</td>
          <td>{{ c.address || '-' }}</td>
          <td class="actions-cell">
            <button class="btn-edit" @click="triggerEdit(c.id)">Edit</button>
            <button class="btn-delete" @click="deleteContact(c.id)">Delete</button>
          </td>
        </tr>
      </tbody>
    </table>

    <div v-if="!loading && contacts.length > 0 && filteredContacts.length === 0" class="empty-state">
      No contacts match your search for "{{ searchQuery }}"
    </div>

    <div v-if="!loading && contacts.length === 0" class="empty-state">
      No contacts found. Go to "Add Entry" to create one!
    </div>
  </div>
</template>

<style scoped>
.management-container { padding: 10px; }

.header-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 25px;
  gap: 20px;
  flex-wrap: wrap;
}

.title-group { display: flex; align-items: center; gap: 10px; }

h2 {
  color: #2c3e50 !important;
  border-left: 5px solid #3498db;
  padding-left: 15px;
  margin: 0;
}

.count-badge {
  background: #ebf4ff;
  color: #3182ce;
  padding: 2px 10px;
  border-radius: 12px;
  font-size: 0.85rem;
  font-weight: bold;
}

.actions-group { display: flex; gap: 10px; flex: 1; justify-content: flex-end; }

/* Search Bar CSS */
.search-bar {
  padding: 8px 15px;
  border: 1px solid #cbd5e0;
  border-radius: 6px;
  width: 100%;
  max-width: 250px;
  outline: none;
  transition: border-color 0.2s;
}

.search-bar:focus { border-color: #3498db; }

/* Table Styling */
.styled-table { width: 100%; border-collapse: collapse; background-color: #fff; }

.styled-table th {
  background-color: #f8f9fa;
  color: #444;
  font-weight: 700;
  padding: 14px 12px;
  text-align: left;
  border-bottom: 2px solid #dee2e6;
}

.styled-table td { padding: 12px; border-bottom: 1px solid #eee; color: #555; }

.actions-cell { text-align: right; white-space: nowrap; }

/* Buttons */
button { cursor: pointer; font-weight: 600; border: none; border-radius: 4px; transition: 0.2s; }

.btn-edit { background-color: #ffc107; color: #212529; padding: 7px 15px; margin-right: 8px; }
.btn-delete { background-color: #dc3545; color: white; padding: 7px 15px; }
.btn-refresh { background-color: #6c757d; color: white; padding: 7px 15px; }

button:hover { opacity: 0.85; transform: translateY(-1px); }

.error-msg { color: #dc3545; background: #f8d7da; padding: 10px; border-radius: 4px; }

.empty-state { text-align: center; padding: 50px; color: #a0aec0; font-style: italic; background: #f7fafc; border-radius: 8px; }
</style>