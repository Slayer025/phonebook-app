<script setup>
import { ref, onMounted, computed } from "vue";
import api from "../services/api";

const contacts = ref([]);
const searchQuery = ref(""); // Stores what the user types

onMounted(async () => {
  try {
    const res = await api.get("/contacts");
    contacts.value = res.data;
  } catch (err) {
    console.error("Error fetching contacts:", err);
  }
});

// This automatically updates whenever 'searchQuery' or 'contacts' change
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
</script>

<template>
  <div class="list-container">
    <div class="list-header">
      <h2>Contact Directory</h2>
      
      <div class="search-box">
        <input 
          v-model="searchQuery" 
          type="text" 
          placeholder="Search by name, phone, or email..."
          class="search-input"
        />
        <span v-if="searchQuery" class="results-count">
          Found {{ filteredContacts.length }} contacts
        </span>
      </div>
    </div>

    <table v-if="filteredContacts.length" class="styled-table">
      <thead>
        <tr>
          <th>Name</th>
          <th>Phone</th>
          <th>Email</th>
          <th>Address</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="c in filteredContacts" :key="c.id">
          <td><b>{{ c.name }}</b></td>
          <td>{{ c.phoneNumber }}</td>
          <td>{{ c.email || '-' }}</td>
          <td>{{ c.address || '-' }}</td>
        </tr>
      </tbody>
    </table>

    <div v-else class="no-results">
      <p v-if="searchQuery">No contacts match "{{ searchQuery }}"</p>
      <p v-else>Loading contacts...</p>
    </div>
  </div>
</template>

<style scoped>
.list-container {
  padding: 10px;
}

.list-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  margin-bottom: 20px;
  gap: 15px;
}

h2 {
  color: #2c3e50 !important;
  border-left: 5px solid #3498db;
  padding-left: 15px;
  margin: 0;
}

/* Search Bar Styles */
.search-box {
  position: relative;
  flex: 1;
  max-width: 400px;
}

.search-input {
  width: 100%;
  padding: 12px 15px;
  border: 2px solid #e2e8f0;
  border-radius: 8px;
  font-size: 1rem;
  transition: all 0.3s ease;
}

.search-input:focus {
  outline: none;
  border-color: #3498db;
  box-shadow: 0 0 0 3px rgba(52, 152, 219, 0.1);
}

.results-count {
  font-size: 0.8rem;
  color: #64748b;
  position: absolute;
  right: 15px;
  top: 50%;
  transform: translateY(-50%);
}

/* Table Styling */
.styled-table {
  width: 100%;
  border-collapse: collapse;
}

.styled-table th, .styled-table td {
  border: 1px solid #edf2f7;
  padding: 12px;
  text-align: left;
}

.styled-table th {
  background-color: #f8fafc;
  color: #475569;
  font-weight: 600;
}

.no-results {
  text-align: center;
  padding: 40px;
  color: #94a3b8;
  background: #f8fafc;
  border-radius: 8px;
  border: 2px dashed #e2e8f0;
}
</style>