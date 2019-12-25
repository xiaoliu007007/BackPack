from django.db import models

# Create your models here.
from django.db import models

class BackPack(models.Model):
    time=models.DateTimeField()
    nai_jishu=models.FloatField()
    nai_jiliang=models.FloatField()
    gm_jishu=models.FloatField()
    gm_jiliang=models.FloatField()
    n_jishu=models.FloatField()
    n_jiliang=models.FloatField()
    longitude=models.FloatField()
    latitude=models.FloatField()
    alarm_status=models.CharField(max_length=10)