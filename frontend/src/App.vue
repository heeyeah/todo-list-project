<template>
  <div id="app">
    <!-- <HelloWorld msg="Welcome to Your Vue.js App" heeye="test heeye"/> -->
    <TodoHeader msg="TODO LIST"></TodoHeader>
    <TodoBody></TodoBody>
  </div>
</template>
<script>
import TodoHeader from './components/TodoHeader.vue'
import TodoBody from './components/TodoBody.vue'

export default {
  name: 'app',
  components: {
    TodoHeader, TodoBody
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
