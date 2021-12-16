<template>
  <div class="modal" :class="{'active': true }">>
    <div class="overlay" @click="$emit('close')"></div>
    <div class="modal-card">
        <p>{{ this.$store.state.year }}년 {{ this.$store.state.month }}월 {{ this.$store.state.day }}일</p>
        <input type="text" v-model="newTodo">
        <button  v-on:click="addTodo">등록</button>
        <ul>
          <li v-for="(todo, index) in todo" :key="index">
            {{todo.title}}
          </li>
        </ul>
    </div>
  </div>
</template>

<script>
export default {
    data() {
        return {
            newTodo: '',
            todos: [],
            // year: this.$store.state.year,
            // month: this.$store.state.month,
            // day: this.$store.state.year,
            // showTodo: []
        }
    },
    created() {
        this.init();
    },
    computed: {
      todo() {
        return this.$store.getters.showTodo
      }
    },
    methods: {
        init() {
        this.todos = this.$store.state.todos || [];
        },
        addTodo() {
            const todoItem = this.newTodo;
            if (!todoItem) return;

            const newTodoItem = {
                title: todoItem,
                // isDone: false,
                createdAt: this.$store.state.year+"-"+this.$store.state.month+"-"+this.$store.state.day
            };
            // this.$store.commit("addNewTodo", newTodoItem);
            console.log();
            this.$store.dispatch("addTodo", newTodoItem);
            this.newTodo = "";
        },
    }

}
</script>

<style>
/* Modal */
.modal,
.overlay {
  width: 100%;
  height: 100%;
  position: fixed;
  left: 0;
  top: 0;
}
.overlay {
  opacity: 0.5;
  background-color: black;
}
.modal-card {
  position: relative;
  max-width: 80%;
  margin: auto;
  margin-top: 30px;
  padding: 20px;
  background-color: white;
  min-height: 500px;
  z-index: 10;
  opacity: 1;
}
</style>