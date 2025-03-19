import os
import psycopg2
from faker import Faker
import random
from datetime import datetime, timedelta

# Настройки подключения
db_name = os.getenv("POSTGRES_DB", "java_bd")
db_user = os.getenv("POSTGRES_USER", "admin")
db_password = os.getenv("POSTGRES_PASSWORD", "admin")
db_host = os.getenv("POSTGRES_HOST", "database")

# Подключение к БД
conn = psycopg2.connect(
    dbname=db_name,
    user=db_user,
    password=db_password,
    host=db_host
)
cur = conn.cursor()


# Заполнение тестовыми данными
fake = Faker()
for i in range(50):
    cur.execute("INSERT INTO students (enrollment_year, full_name, student_id) VALUES (%s, %s, %s) ON CONFLICT DO NOTHING",
                (2000, fake.name(), i ))

courses = []
for i in range(10):  # Предположим, создаем 10 курсов
    course_code = f"CSE{random.randint(100, 999)}"
    credits = random.randint(2, 6)
    lecturer = fake.name()
    name = fake.word().capitalize() + " Course"

    courses.append((i, course_code, credits, lecturer, name))

for course in courses:
    cur.execute(
        "INSERT INTO courses (id, course_code, credits, lecturer, name) VALUES (%s, %s, %s, %s, %s)",
        course
    )

# Возможные статусы для записей в enrollments
statuses = ["active", "completed", "dropped", "pending"]

#for i in range(50):
 #   enrollment_date = datetime(2020, 1, 1) + timedelta(days=random.randint(0, 1500))
  #  status = random.choice(statuses)
   # course_id = random.randint(1, 10)  # Предположим, у вас есть 10 курсов
    #student_id = i  # Связываем с тем же student_id, который использовался ранее

    #cur.execute(
    #    "INSERT INTO enrollments (enrollment_date, status, course_id, student_id) VALUES (%s, %s, %s, %s)",
    #    (enrollment_date, status, course_id, student_id)
    #)



# Завершение
conn.commit()
cur.close()
conn.close()
