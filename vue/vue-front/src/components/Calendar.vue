<template>
  <div>
    <div>
      <div class="header">
        <i
          class="fas fa-angle-left arrowLeft"
          v-on:click="controlMonth('prev')"
        ></i>
        <h1>{{ year }}.{{ month }}</h1>
        <i
          class="fas fa-angle-right arrowRight"
          v-on:click="controlMonth('next')"
        ></i>
      </div>

      <table>
        <thead>
          <th v-for="day in days" :key="day">{{ day }}</th>
        </thead>
        <tbody>
          <tr v-for="(date, idx) in dates" :key="idx">
            <td
              v-for="(day, index) in date"
              :key="index"
              :class="{}"
              v-on:click="clickDate(day)"
              class="pointer"
            >
              <div>
                {{ day }}
              </div>
              <div
                v-if="getMatchedTodos(day).length > 0 && !isPrevDates(day, idx)"
              >
                <div v-for="(todo, idx) in getMatchedTodos(day)" :key="idx">
                  {{ todo.title }}
                </div>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <todo-list
      v-show="showModal"
      v-bind:open="showModal"
      @close="showModal = false"
    >
      <!-- <h3 slot="header"></h3> -->
    </todo-list>
  </div>
</template>

<script>
import TodoList from "./TodoList.vue";
export default {
  components: {
    TodoList
  },
  data() {
    return {
      days: ["일", "월", "화", "수", "목", "금", "토"],
      dates: [],
      year: 0,
      month: 0,
      currentDate: new Date().getDate(),
      currentYear: 0,
      currentMonth: 0,
      clickDay: 0,
      prevDate: [],
      previewDate: [],
      showModal: false
    };
  },
  computed: {
    isCurrentDate() {
      let status = false;
      if (this.currentYear === 0 && this.currentMonth === 0) {
        status = true;
      } else {
        status =
          this.currentYear === new Date().getFullYear() &&
          this.currentMonth === new Date().getMonth() + 1;
      }
      return status;
    }
  },
  created() {
    this.init();
  },
  methods: {
    init(param) {
      if (param) {
        this.year = param[0];
        this.month = param[1];
        this.calendarDate();
      } else {
        const date = new Date();
        this.year = date.getFullYear();
        this.month = date.getMonth() + 1;
        this.calendarDate();
      }
    },
    calendarDate() {
      const [
        monthFirstDay,
        monthLastDate,
        prevMonthLastDate
      ] = this.getFirstDayLastDate(this.year, this.month);
      this.dates = this.getDaysOfMonth(
        monthFirstDay,
        monthLastDate,
        prevMonthLastDate
      );
    },
    getFirstDayLastDate(year, month) {
      const firstDay = new Date(year, month - 1, 1).getDay();
      const lastDate = new Date(year, month, 0).getDate();
      let lastMonth = month - 1;
      if (month === 1) {
        lastMonth = 12;
        year -= 1;
      }
      const prevLastDate = new Date(year, lastMonth, 0).getDate();
      return [firstDay, lastDate, prevLastDate];
    },
    getDaysOfMonth(monthFirstDay, monthLastDate, prevMonthLastDate) {
      let day = 1;
      let prevDay = prevMonthLastDate - monthFirstDay + 1;
      let dates = [];
      let daysOfWeek = [];
      while (day <= monthLastDate) {
        if (day === 1) {
          this.getPrevDates(monthFirstDay, daysOfWeek, prevDay);
          this.padDates(daysOfWeek);
        }
        if (daysOfWeek.length === 7) {
          dates.push(daysOfWeek);
          day = daysOfWeek[daysOfWeek.length - 1];
          daysOfWeek = [];
        } else if (
          daysOfWeek.length < 7 &&
          daysOfWeek.indexOf(monthLastDate) > -1
        ) {
          this.padDates(daysOfWeek);
          dates.push(daysOfWeek);
          break;
        }
        day++;
        if (daysOfWeek.length <= 7) {
          daysOfWeek.push(day);
        }
      }
      return dates;
    },
    getPrevDates(monthFirstDay, daysOfWeek, prevDay) {
      for (let j = 0; j < monthFirstDay; j++) {
        daysOfWeek.push(prevDay);
        this.prevDate.push(prevDay);
        prevDay += 1;
      }
    },
    padDates(daysOfWeek) {
      const len = daysOfWeek.length;
      const leftDays = 7 - len;
      if (len >= 0 && len < 7) {
        for (let i = 1; i <= leftDays; i++) {
          daysOfWeek.push(i);
          if (this.previewDate.length < leftDays) this.previewDate.push(i);
        }
      }
    },
    controlMonth(p) {
      if (p === "prev") {
        this.currentMonth = this.month - 1;
        this.currentYear = this.year;
        if (this.month === 1) {
          this.currentMonth = 12;
          this.currentYear = this.year -= 1;
        }
      } else {
        this.currentMonth = this.month + 1;
        this.currentYear = this.year;
        if (this.month === 12) {
          this.currentMonth = 1;
          this.currentYear = this.year += 1;
        }
      }
      const param = [this.currentYear, this.currentMonth];
      this.init(param);
    },
    getMatchedTodos(day) {
      return this.$store.state.todos.filter(todo => {
        const todoDate = new Date(todo.createdAt);
        const isDateMatched = todoDate.getDate() === day;
        const isMonthMatched = todoDate.getMonth() === this.month - 1;
        const isYearMathced = todoDate.getFullYear() === this.year;
        return isDateMatched && isMonthMatched && isYearMathced;
      });
    },
    isPrevDates(day, idx) {
      return (
        (this.prevDate.indexOf(day) > -1 && idx < 1) ||
        (this.previewDate.indexOf(day) > -1 && idx > 1)
      );
    },
    clickDate(day) {
      this.showModal = !this.showModal;
      this.$store.commit("clickDate", {
        year: this.year,
        month: this.month,
        day: day
      });
    }
  }
};
</script>

<style scoped>
div {
  text-align: center;
  /* position: relative; */
  margin: 0 auto;
}
h1 {
  justify-content: center;
  text-align: center;
}
.header {
  position: relative;
  justify-content: center;
  text-align: center;
  /* padding: 0 auto; */
}
.arrowLeft {
  position: absolute;
  justify-content: center;
  left: 10px;
  top: 10px;
}
.arrowRight {
  position: absolute;
  justify-content: center;
  right: 10px;
  bottom: 10px;
}
table {
  margin: 0 auto;
  text-align: center;
  border-collapse: collapse;
  width: 400px;
  height: 500px;
  /* border-spacing: 20px 30px; */
}
th {
  border-bottom: 1px solid;
  /* background-color: ; */
  /* vertical-align: top; */
}
td {
  border-bottom: 1px solid;
  /* padding: 20px;
  font-size: 20px; */
  vertical-align: top;
}
.pointer {
  cursor: pointer;
}
</style>
