<script setup>
import { ref } from "vue";
import ContactList from "./components/ContactList.vue";
import ContactManagement from "./components/ContactManagement.vue";
import ContactForm from "./components/ContactForm.vue";
import ContactEdit from "./components/ContactEdit.vue"; // <--- MAKE SURE THIS IS HERE

const view = ref("list");
const selectedId = ref(null);

const openEdit = (id) => {
  console.log("Opening edit for ID:", id); // Check if this logs in F12 console
  selectedId.value = id;
  view.value = "edit";
};
</script>
<template>
  <div class="app-wrapper">
    <header class="main-header">
      <div class="header-content">
        <h1>Phonebook Manager</h1>
        <nav class="navigation">
          <button 
            :class="{ active: view === 'list' }" 
            @click="view = 'list'"
          >
            View List
          </button>
          <button 
            :class="{ active: view === 'manage' }" 
            @click="view = 'manage'"
          >
            Manage Contacts
          </button>
          <button 
            :class="{ active: view === 'add' }" 
            @click="view = 'add'"
          >
            Add Entry
          </button>
        </nav>
      </div>
    </header>

    <main class="main-content">
      <div class="card-container">
        <ContactList v-if="view === 'list'" />
        
        <ContactManagement v-if="view === 'manage'" @edit="openEdit" />
        
        <ContactForm v-if="view === 'add'" @done="view = 'list'" />

        <ContactEdit 
          v-if="view === 'edit'" 
          :id="selectedId" 
          @done="view = 'manage'" 
        />
      </div>
    </main>
  </div>
</template>

<style>
/* 1. Global Reset */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: 'Segoe UI', Arial, sans-serif;
  background-color: #f0f4f8;
}

/* 2. Header - Fixing the "Phonebook Manager" Title */
.main-header {
  background-color: #1a252f; /* Darker Navy */
  color: #ffffff !important; /* Forces White */
  padding: 2.5rem 1rem;
  text-align: center;
  box-shadow: 0 4px 10px rgba(0,0,0,0.2);
}

.main-header h1 {
  color: #ffffff !important; /* Explicitly White */
  font-size: 2.2rem;
  margin-bottom: 1.5rem;
  text-shadow: 2px 2px 4px rgba(0,0,0,0.3);
}

/* 3. Navigation Buttons */
.navigation {
  display: flex;
  justify-content: center;
  gap: 12px;
}

.navigation button {
  padding: 10px 22px;
  background: #34495e;
  color: #ecf0f1;
  border: 1px solid #ffffff33;
  border-radius: 6px;
  cursor: pointer;
  font-weight: 600;
  transition: 0.2s;
}

.navigation button.active {
  background-color: #3498db; /* Bright Blue */
  color: #ffffff;
  border-color: #3498db;
  box-shadow: 0 0 15px rgba(52, 152, 219, 0.4);
}

/* 4. Content Area - Fixing "Contact Directory / Manage Contacts" */
.main-content {
  max-width: 1100px;
  margin: 30px auto;
  padding: 20px;
}

.card-container {
  background: white;
  padding: 35px;
  border-radius: 12px;
  box-shadow: 0 8px 30px rgba(0,0,0,0.1);
}

/* TARGETING THE GREY TITLES INSIDE COMPONENTS */
.card-container h2 {
  color: #2c3e50 !important; /* Deep Dark Navy - NOT GREY */
  font-size: 1.6rem;
  margin-bottom: 20px;
  border-left: 5px solid #3498db;
  padding-left: 15px;
}

/* Table Text Visibility */
table th {
  color: #1a252f;
  font-weight: 700;
  text-transform: uppercase;
  font-size: 0.85rem;
}

table td {
  color: #333333;
}
</style>