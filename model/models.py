from django.db import models

# Create your models here.
from django.db import models

class BackPack(models.Model):
    time=models.DateTimeField()
    values=models.FloatField()
    longitude=models.FloatField()
    latitude=models.FloatField()