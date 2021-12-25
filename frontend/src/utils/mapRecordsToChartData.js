const mapRecordsToChartData = records => {
  let finalRecords = [];
  for (let i = 0; i < 24; i++) {
    records.forEach(recordReports => {
      recordReports.forEach(report => {
        const key = Object.keys(report).find(
          key => key === 'deviceDescription'
        );
        if (
          report.timestamp &&
          parseInt(report.timestamp.substring(0, 2)) === i
        ) {
          const reportAvailable = finalRecords.find(
            r => r.hour === parseInt(report.timestamp.substring(0, 2))
          );
          if (!reportAvailable) {
            finalRecords.push({
              hour: i,
              [report[key]]: report.energyConsumption,
            });
          } else {
            const index = finalRecords.findIndex(
              r => r.hour === reportAvailable.hour
            );
            finalRecords[index] = {
              ...finalRecords[index],
              [report[key]]: report.energyConsumption,
            };
          }
        }
      });
    });
    records.forEach(recordReports => {
      const reportAvailable = finalRecords.find(r => r.hour === i);
      const key = Object.keys(recordReports[0]).find(
        key => key === 'deviceDescription'
      );
      if (!reportAvailable) {
        finalRecords.push({
          hour: i,
          [recordReports[0][key]]: 0,
        });
      } else {
        const index = finalRecords.findIndex(r => r.hour === i);
        if (!finalRecords[index][recordReports[0][key]]) {
          finalRecords[index] = {
            ...finalRecords[index],
            [recordReports[0][key]]: 0,
          };
        }
      }
    });
  }

  return finalRecords;
};

export default mapRecordsToChartData;
